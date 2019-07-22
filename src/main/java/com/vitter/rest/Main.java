package com.vitter.rest;
import static spark.Spark.*;
import com.vitter.rest.util.TextUtils;

public class Main {
	
    public String analyzetext(String requestBody) {
        String response = "Text Analyzer Results: \n";
        TextUtils textutils = new TextUtils();
        response = response + "Number of letters: " + textutils.countLetters(requestBody) + "\n";
        response = response + "Number of words: " + textutils.countWords(requestBody) + "\n";
        response = response + "Most repeated word: " + textutils.mostRepeatedWord(requestBody) + "\n";

        return response;
    }
	
	
	public static void main(String[] args) {
		//
		System.out.println("Starting our simple rest server...");
		System.out.println("Hello World Example: http://localhost:4567/hello");
		Main main = new Main();
		// Basic Hello World get example:
        get("/hello", (req, res) -> "Hello World!");

        // a function that returns the following:
        // number of letters, words, and sentences
        // most repeated word.
        post("/analyzetext", (req, res) -> main.analyzetext(req.body().toString()));
    }
	
}
