drop table if exists atm_allocation;
drop table if exists atm;
drop table if exists denomination_type;
drop table if exists denomination;
drop table if exists client_account;
drop table if exists client;
drop table if exists credit_card_limit;
drop table if exists currency_conversion_rate;
drop table if exists client_type;
drop table if exists client_sub_type;
drop table if exists account_type;
drop table if exists currency;

/***************************************************************************************
 *  ACCOUNT TYPES                                                                      *
 *  TRANSACTIONAL determines if a client can use the card to pay at a POS or draw      *
 *     money at an ATM on the specified account                                        *
 ***************************************************************************************/

CREATE TABLE IF NOT EXISTS ACCOUNT_TYPE (
  ACCOUNT_TYPE_CODE VARCHAR(10) NOT NULL PRIMARY KEY,
  DESCRIPTION       VARCHAR(50) NOT NULL,
  TRANSACTIONAL     BIT
);

/*
****************************************************************************************
 *  CLIENT TYPE                                                                        *
 ***************************************************************************************
 */

CREATE TABLE IF NOT EXISTS CLIENT_TYPE (
  CLIENT_TYPE_CODE VARCHAR(2)   NOT NULL PRIMARY KEY,
  DESCRIPTION      VARCHAR(255) NOT NULL
);

/***************************************************************************************
 *  CLIENT SUB TYPE                                                                        *
 ***************************************************************************************/

CREATE TABLE IF NOT EXISTS CLIENT_SUB_TYPE (
  CLIENT_SUB_TYPE_CODE VARCHAR(4)   NOT NULL PRIMARY KEY,
  CLIENT_TYPE_CODE     VARCHAR(2)   NOT NULL REFERENCES CLIENT_TYPE (CLIENT_TYPE_CODE),
  DESCRIPTION          VARCHAR(255) NOT NULL
);

/***************************************************************************************
 *  List of currency codes for use in the formatting test                              *
 ***************************************************************************************/

CREATE TABLE IF NOT EXISTS CURRENCY (
  CURRENCY_CODE  VARCHAR(3)   NOT NULL PRIMARY KEY,
  DECIMAL_PLACES INTEGER      NOT NULL,
  DESCRIPTION    VARCHAR(255) NOT NULL
);

/***************************************************************************************
 * Currency conversion rats                                                            *
 * The CONVERSION_INDICATOR specifies if the foreign amount should be multiplied ('*') *
 * or divided when converting to the local amount.                                     *
 *                                                                                     *
 *  i.e. USD 12 = 12 * 11.6167                                                         *
 *              = 139.40 ZAR                                                           *
 *                                                                                     *
 *                                                                                     *
 *       AUD 12 = 12 / 0.1134                                                          *
 *              = 105.82 ZAR                                                           *
 ***************************************************************************************/

CREATE TABLE IF NOT EXISTS CURRENCY_CONVERSION_RATE (
  CURRENCY_CODE        VARCHAR(3)     NOT NULL PRIMARY KEY REFERENCES CURRENCY (CURRENCY_CODE),
  CONVERSION_INDICATOR VARCHAR(1)     NOT NULL,
  RATE                 DECIMAL(18, 8) NOT NULL
);

/***************************************************************************************
 *  Table containing the types of "material" in which denominations are issued         *
 ***************************************************************************************/

CREATE TABLE IF NOT EXISTS DENOMINATION_TYPE (
  DENOMINATION_TYPE_CODE VARCHAR(1)   NOT NULL PRIMARY KEY,
  DESCRIPTION            VARCHAR(255) NOT NULL
);

/***************************************************************************************
 *  Table containing all the denominations for the purposes of the test                *
 *  Denomination types are either C or N where C indicates a "Coin" and N indicates a  *
 *  "Note"*                                                                            *
 ***************************************************************************************/

CREATE TABLE IF NOT EXISTS DENOMINATION (
  DENOMINATION_ID        INTEGER IDENTITY PRIMARY KEY,
  VALUE                  DECIMAL NOT NULL,
  DENOMINATION_TYPE_CODE VARCHAR(1) REFERENCES DENOMINATION_TYPE (DENOMINATION_TYPE_CODE)
);

/***************************************************************************************
 *  CLIENT DATA                                                                      *
 ***************************************************************************************/

CREATE TABLE IF NOT EXISTS CLIENT (
  CLIENT_ID            INTEGER IDENTITY PRIMARY KEY,
  TITLE                VARCHAR(10),
  NAME                 VARCHAR(255) NOT NULL,
  SURNAME              VARCHAR(100),
  DOB                  DATE         NOT NULL,
  CLIENT_SUB_TYPE_CODE VARCHAR(4)   NOT NULL REFERENCES CLIENT_SUB_TYPE (CLIENT_SUB_TYPE_CODE)
);

/***************************************************************************************
 *  CLIENT ACCOUNT BALANCES                                                            *
 *  SVGS and CFCA list positive account balances                                       *
 *  CHQ accounts allow being overdrawn and can have either a positive or               *
 *       negative balance                                                              *
 *  CCRD lists the current *available* balance on a credit card account, card limits   *
 *       are stored on the CREDIT_CARD_LIMIT table and lists the maximum credit amount *
 *       credit card accounts do now allow being overdrawn                             *
 *  PLOAN and HLOAN list outstanding balances on the loan amounts as negative amounts  *
 ***************************************************************************************/

CREATE TABLE IF NOT EXISTS CLIENT_ACCOUNT (
  CLIENT_ACCOUNT_NUMBER VARCHAR(10) IDENTITY PRIMARY KEY,
  CLIENT_ID             INTEGER     NOT NULL REFERENCES CLIENT (CLIENT_ID),
  ACCOUNT_TYPE_CODE     VARCHAR(10) NOT NULL REFERENCES ACCOUNT_TYPE (ACCOUNT_TYPE_CODE),
  CURRENCY_CODE         VARCHAR(3)  NOT NULL REFERENCES CURRENCY (CURRENCY_CODE),
  DISPLAY_BALANCE       NUMERIC(18, 3)
);

/*
****************************************************************************************
 *  CREDIT CARD LIMITS                                                                 *
 *  Lists the credit amount that a client is allowed to access on the specified        *
 *  Credit Card account                                                                *
 ***************************************************************************************
 */

CREATE TABLE IF NOT EXISTS CREDIT_CARD_LIMIT (
  CLIENT_ACCOUNT_NUMBER VARCHAR(10)    NOT NULL PRIMARY KEY REFERENCES CLIENT_ACCOUNT (CLIENT_ACCOUNT_NUMBER),
  ACCOUNT_LIMIT         DECIMAL(18, 3) NOT NULL
);

/***************************************************************************************
 *  ATM                                                                                *
 *  Lists the ID and location of each ATM                                              *
 ***************************************************************************************/

CREATE TABLE IF NOT EXISTS ATM (
  ATM_ID   INTEGER IDENTITY  PRIMARY KEY,
  NAME     VARCHAR(10)  NOT NULL UNIQUE,
  LOCATION VARCHAR(255) NOT NULL
);


/***************************************************************************************
 *  ATM ALLOCATION                                                                     *
 *  Stores the number of notes per denomination available to each ATM                  *
 ***************************************************************************************/

CREATE TABLE IF NOT EXISTS ATM_ALLOCATION (
  ATM_ALLOCATION_ID INTEGER IDENTITY PRIMARY KEY,
  ATM_ID            INTEGER NOT NULL REFERENCES ATM (ATM_ID),
  DENOMINATION_ID   INTEGER NOT NULL REFERENCES DENOMINATION (DENOMINATION_ID),
  COUNT             INTEGER NOT NULL
);
