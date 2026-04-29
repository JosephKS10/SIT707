package web.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for LoginService - developer view.
 *
 * Combines three black-box techniques:
 *   1. Equivalence Class Partitioning (ECP) - one representative per
 *      valid/invalid class for each input.
 *   2. Boundary Value Analysis (BVA)        - probe DoB at format and
 *      calendar boundaries.
 *   3. Decision Table                       - all 8 boolean combinations
 *      of (valid_user, valid_pass, valid_dob).
 */

public class LoginServiceUnitTest {

    /* --------- Happy path (decision table rule R1) --------- */

    @Test public void login_validCredentials_returnsTrue() {
        assertTrue(LoginService.login("ahsan", "ahsan_pass", "1990-05-15"));
    }
    @Test public void login_secondRegisteredUser_returnsTrue() {
        assertTrue(LoginService.login("joseph", "joseph_pass", "1998-08-20"));
    }
    @Test public void login_thirdRegisteredUser_returnsTrue() {
        assertTrue(LoginService.login("alice", "alice123", "2000-01-01"));
    }

    /* --------- ECP: invalid USERNAME --------- */

    @Test public void login_nullUsername_returnsFalse() {
        assertFalse(LoginService.login(null, "ahsan_pass", "1990-05-15"));
    }
    @Test public void login_emptyUsername_returnsFalse() {
        assertFalse(LoginService.login("", "ahsan_pass", "1990-05-15"));
    }
    @Test public void login_blankUsername_returnsFalse() {
        assertFalse(LoginService.login("   ", "ahsan_pass", "1990-05-15"));
    }
    @Test public void login_unregisteredUsername_returnsFalse() {
        assertFalse(LoginService.login("nobody", "ahsan_pass", "1990-05-15"));
    }

    /* --------- ECP: invalid PASSWORD --------- */

    @Test public void login_nullPassword_returnsFalse() {
        assertFalse(LoginService.login("ahsan", null, "1990-05-15"));
    }
    @Test public void login_emptyPassword_returnsFalse() {
        assertFalse(LoginService.login("ahsan", "", "1990-05-15"));
    }
    @Test public void login_wrongPassword_returnsFalse() {
        assertFalse(LoginService.login("ahsan", "WRONG", "1990-05-15"));
    }
    @Test public void login_otherUsersPassword_returnsFalse() {
        assertFalse(LoginService.login("ahsan", "sarthak_pass", "1990-05-15"));
    }

    /* --------- ECP: invalid DOB value --------- */

    @Test public void login_nullDob_returnsFalse() {
        assertFalse(LoginService.login("ahsan", "ahsan_pass", null));
    }
    @Test public void login_emptyDob_returnsFalse() {
        assertFalse(LoginService.login("ahsan", "ahsan_pass", ""));
    }
    @Test public void login_wrongDob_returnsFalse() {
        assertFalse(LoginService.login("ahsan", "ahsan_pass", "1991-05-15"));
    }

    /* --------- BVA: DoB string format / calendar boundaries --------- */

    @Test public void login_dobMissingLeadingZeroOnMonth_returnsFalse() {
        assertFalse(LoginService.login("ahsan", "ahsan_pass", "1990-5-15"));
    }
    @Test public void login_dobWrongDelimiter_returnsFalse() {
        assertFalse(LoginService.login("ahsan", "ahsan_pass", "1990/05/15"));
    }
    @Test public void login_dobAlphaCharacters_returnsFalse() {
        assertFalse(LoginService.login("ahsan", "ahsan_pass", "abcd-ef-gh"));
    }
    @Test public void login_dobMonthOutOfRange_returnsFalse() {
        assertFalse(LoginService.login("ahsan", "ahsan_pass", "1990-13-15"));
    }
    @Test public void login_dobMonthZero_returnsFalse() {
        assertFalse(LoginService.login("ahsan", "ahsan_pass", "1990-00-15"));
    }
    @Test public void login_dobInvalidDayForMonth_returnsFalse() {
        assertFalse(LoginService.login("ahsan", "ahsan_pass", "1990-02-30"));
    }
    @Test public void login_dobDayOutOfRange_returnsFalse() {
        assertFalse(LoginService.login("ahsan", "ahsan_pass", "1990-05-32"));
    }

    /* --------- Decision table rules R2-R8 --------- */
    /* Rule | username | password | dob   | expected
     *  R1  | valid    | valid    | valid | TRUE  (covered above)
     *  R2  | valid    | valid    | bad   | FALSE
     *  R3  | valid    | bad      | valid | FALSE
     *  R4  | valid    | bad      | bad   | FALSE
     *  R5  | bad      | valid    | valid | FALSE
     *  R6  | bad      | valid    | bad   | FALSE
     *  R7  | bad      | bad      | valid | FALSE
     *  R8  | bad      | bad      | bad   | FALSE
     */
    @Test public void decisionTable_R2_validUser_validPass_badDob() {
        assertFalse(LoginService.login("ahsan", "ahsan_pass", "1991-05-15"));
    }
    @Test public void decisionTable_R3_validUser_badPass_validDob() {
        assertFalse(LoginService.login("ahsan", "WRONG", "1990-05-15"));
    }
    @Test public void decisionTable_R4_validUser_badPass_badDob() {
        assertFalse(LoginService.login("ahsan", "WRONG", "1991-05-15"));
    }
    @Test public void decisionTable_R5_badUser_validPass_validDob() {
        assertFalse(LoginService.login("nobody", "ahsan_pass", "1990-05-15"));
    }
    @Test public void decisionTable_R6_badUser_validPass_badDob() {
        assertFalse(LoginService.login("nobody", "ahsan_pass", "1991-05-15"));
    }
    @Test public void decisionTable_R7_badUser_badPass_validDob() {
        assertFalse(LoginService.login("nobody", "WRONG", "1990-05-15"));
    }
    @Test public void decisionTable_R8_allBad() {
        assertFalse(LoginService.login("nobody", "WRONG", "abcd-ef-gh"));
    }

    /* --------- Helper-method tests (push branch coverage past 90%) --------- */

    @Test public void isPresent_acceptsNonBlankString() { assertTrue(LoginService.isPresent("x")); }
    @Test public void isPresent_rejectsNull()          { assertFalse(LoginService.isPresent(null)); }
    @Test public void isPresent_rejectsEmpty()         { assertFalse(LoginService.isPresent("")); }
    @Test public void isPresent_rejectsWhitespace()    { assertFalse(LoginService.isPresent("\t  \n")); }

    @Test public void isValidDobFormat_acceptsRealDate()       { assertTrue(LoginService.isValidDobFormat("2000-12-31")); }
    @Test public void isValidDobFormat_rejectsNull()           { assertFalse(LoginService.isValidDobFormat(null)); }
    @Test public void isValidDobFormat_rejectsImpossibleDate() { assertFalse(LoginService.isValidDobFormat("2023-02-30")); }

    @Test public void getRegisteredUsers_containsSeededAccounts() {
        assertEquals(3, LoginService.getRegisteredUsers().size());
        assertTrue(LoginService.getRegisteredUsers().containsKey("ahsan"));
    }
}