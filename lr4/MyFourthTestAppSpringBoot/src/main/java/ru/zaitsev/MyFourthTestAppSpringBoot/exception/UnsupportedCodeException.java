package ru.zaitsev.MyFourthTestAppSpringBoot.exception;

public class UnsupportedCodeException extends RuntimeException {
    public UnsupportedCodeException(String message) { super(message); }
}