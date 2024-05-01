/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2023. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package com.nhnacademy.number.impl;

import com.nhnacademy.number.MyNumber;

import javax.naming.OperationNotSupportedException;

public class RationalNumber implements MyNumber {
    private int numerator; // 분자
    private int denominator; // 분모

    public RationalNumber(int n) {
        this.numerator = n;
        this.denominator = 1;
    }

    public RationalNumber(int n1, int n2) {
        if (n2 == 0)
            throw new IllegalStateException();

        this.numerator = n1;
        this.denominator = n2;
        simplify();
    }

    public RationalNumber(MyNumber operand) {
        // RationalNumber 인스턴스일 경우, 해당 필드 접근
        if (!(operand instanceof RationalNumber)) {
            throw new IllegalArgumentException();
        }
        RationalNumber rn = (RationalNumber) operand;
        this.numerator = rn.numerator;
        this.denominator = rn.denominator;
    }


    // 유리수 덧셈
    // 분자끼리 더하기 and 분모는 통분(곱셈)
    @Override
    public MyNumber plus(MyNumber operand) throws OperationNotSupportedException {
        if (operand instanceof RationalNumber) {
            RationalNumber rn = (RationalNumber) operand;
            int num = this.numerator * rn.denominator + rn.numerator * this.denominator;
            int denom = this.denominator * rn.denominator;
            return new RationalNumber(num, denom);
        }
        throw new OperationNotSupportedException();
    }
    // 유리수 뺄셈
    // 분자 빼기 and 분모 통분(곱셈)
    @Override
    public MyNumber minus(MyNumber operand) throws OperationNotSupportedException {
        if (operand instanceof RationalNumber) {
            RationalNumber rationalNumber = (RationalNumber) operand;
            int num = this.numerator * rationalNumber.denominator - rationalNumber.numerator * this.denominator;
            int denom = this.denominator * rationalNumber.denominator;
            return new RationalNumber(num, denom);
        }
        throw new OperationNotSupportedException();
    }

    // 유리수 곱셈
    // 분자, 분모 각각 곱셈
    @Override
    public MyNumber multipliedBy(MyNumber operand) throws OperationNotSupportedException {
        if (operand instanceof RationalNumber) {
            RationalNumber rationalNumber = (RationalNumber) operand;
            int num = this.numerator * rationalNumber.numerator;
            int denom = this.denominator * rationalNumber.denominator;
            return new RationalNumber(num, denom);
        }
        throw new OperationNotSupportedException();
    }

    // 유리수 나눗셈
    // 분자는 상대 분모와 곱하고 분모는 상대 분자와 곱셈
    @Override
    public MyNumber divideBy(MyNumber operand) throws OperationNotSupportedException {
        if (operand instanceof RationalNumber) {
            RationalNumber rationalNumber = (RationalNumber) operand;
            if (rationalNumber.numerator == 0) {
                throw new IllegalArgumentException();
            }
            int num = this.numerator * rationalNumber.denominator;
            int denom = this.denominator * rationalNumber.numerator;
            return new RationalNumber(num, denom);
        }
        throw new OperationNotSupportedException();
    }

    int gcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }
    // 기약분수 간소화
    void simplify() {
        int gcd = gcd(this.numerator, this.denominator);
        this.numerator /= gcd;
        this.denominator /= gcd;
    }

    // 유리수의 문자열 표현
    public String toString() {
        if (denominator == 1) { // 분모 1인 경우 (정수)
            return String.valueOf(numerator);
        } else if (numerator == 0) { // 분모 0인 경우 (정수 0)
            return "0";
        } else // 분수 형태로 나오는 경우 ( / 를 사용한 표현)
            return numerator + "/" + denominator;
    }

    // 두 유리수가 서로 같은 지 비교
    public boolean equals(Object o) {
        if(this == o) return true;
        if (!(o instanceof RationalNumber)) return false;
        RationalNumber rationalNumber = (RationalNumber) o; // 비교하는 객체를 RationalNumber 타입으로 형변환
        // 두 유리수의 분자와 분모가 각각 같은지 확인
        return this.numerator == rationalNumber.numerator && this.denominator == rationalNumber.denominator;
    }
    // 양수 판별
    public boolean isPositive() {
        return (numerator > 0 && denominator > 0)
                || (numerator < 0 && denominator < 0);
    }

    // 테스트를 통과하기 위한 정렬 메소드
    public int compareTo(RationalNumber rn) {
        long num = (long) this.numerator * rn.denominator;
        long denom = (long) rn.numerator * this.denominator;
        return Long.compare(num, denom);
    }
}
