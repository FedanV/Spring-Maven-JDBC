package com.foxminded.vitaliifedan.task4.models;

public class Division {

    private final StringBuilder result = new StringBuilder();
    private final StringBuilder reminder = new StringBuilder();
    private final StringBuilder quotient = new StringBuilder();

    public StringBuilder getReminder() {
        return reminder;
    }

    public StringBuilder getQuotient() {
        return quotient;
    }

    public StringBuilder makeDivision(int dividend, int divisor) {

        String[] digits = String.valueOf(dividend).split("");
        int reminderNumber;
        int subtractedNumber;
        int reminderOfDivision;

        for (int i = 0; i < digits.length; i++) {

            reminder.append(digits[i]);
            reminderNumber = Integer.parseInt(reminder.toString());

            if (reminderNumber >= divisor) {

                reminderOfDivision = reminderNumber % divisor;
                subtractedNumber = reminderNumber / divisor * divisor;
                String lastReminder = String.format("%" + (i + 2) + "s", "_" + reminderNumber);
                result.append(lastReminder).append("\n");
                String subtrahend = String.format("%" + (i + 2) + "d", subtractedNumber);
                result.append(subtrahend).append("\n");
                int countOfIndents = lastReminder.length() - calculateDigits(subtractedNumber);
                result.append(makeDivider(reminderNumber, countOfIndents)).append("\n");
                quotient.append(reminderNumber / divisor);
                reminder.replace(0, reminder.length(), String.valueOf(reminderOfDivision));
                reminderNumber = Integer.parseInt(reminder.toString());

            }

            if (i == digits.length - 1) {
                result.append(String.format("%" + (i + 2) + "s", reminderNumber)).append("\n");
            }
        }
        return result;
    }

    private String makeDivider(int reminderNumber, int space) {
        return assebleString(space, ' ') + assebleString(calculateDigits(reminderNumber), '-');
    }

    public String assebleString(int numberOfSymbols, char symbol) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < numberOfSymbols; i++) {
            string.append(symbol);
        }
        return string.toString();
    }

    public int calculateDigits(int divisor) {
        return String.valueOf(divisor).split("").length;
    }


}