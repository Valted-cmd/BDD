package ru.netology.test;


import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelp;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransfer {

    public int sum = 500;
    public DataHelp.CardInfo getFirstCard = DataHelp.getFirstCard();
    public DataHelp.CardInfo getSecondCard = DataHelp.getSecondCard();


    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelp.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelp.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstNumber = DataHelp.getFirstCard();
        var secondNumber = DataHelp.getSecondCard();
        int startBalance1 = dashboardPage.getFirstCardBalance();
        int startBalance2 = dashboardPage.getSecondCardBalance();
        var transferPage = dashboardPage.chooseCard();
        transferPage.transferMoneyFromCard2ToCard1(sum, getSecondCard);

        var dashBoardPageFinish = new DashboardPage();
        int finishBalance1 = dashBoardPageFinish.getFirstCardBalance();
        int finishBalance2 = dashBoardPageFinish.getSecondCardBalance();

        assertEquals(startBalance2 - sum, finishBalance2);
        assertEquals(startBalance1 + sum, finishBalance1);
    }




}