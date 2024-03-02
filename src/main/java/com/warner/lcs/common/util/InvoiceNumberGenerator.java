package com.warner.lcs.common.util;

import java.util.Random;

public class InvoiceNumberGenerator {
    private Random random;
    private final int ALPHABET_ARRAY_SIZE = 26, NUMBER_ARRAY_SIZE = 10, INVOCE_NO_LENGTH = 8;
    char[] charArray;
    public InvoiceNumberGenerator() {
        // Size of the array (10 digits + 26 capital letters)
        int size = 36;

        // Create a Random object
         this.random = new Random();

        // Generate a random number from 0 to 35
//        int randomNumber = random.nextInt(36);

        // Initialize the character array
         charArray = new char[size];

        // Populate the array with '0' to '9'
        for (int i = 0; i < 10; i++) {
            charArray[i] = (char) ('0' + i);
        }

        // Populate the array with 'A' to 'Z'
        for (int i = 10; i < 36; i++) {
            charArray[i] = (char) ('A' + (i - 10));
        }

        // Print the contents of the array
        for (char c : charArray) {
            System.out.print(c + " ");
        }
    }

    private int getRandomNumber(int min,int max){
        return random.nextInt(max - min) + min;
    }

    public String generateInvoiceNo(){

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < INVOCE_NO_LENGTH; i++) {
            if(i < 2){
                sb.append(this.charArray[this.getRandomNumber(9,35)]);
            }else {sb.append(this.charArray[this.getRandomNumber(0,9)]);}
        }
        return sb.toString();
    }


}
