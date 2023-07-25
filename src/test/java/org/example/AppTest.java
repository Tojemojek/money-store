package org.example;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    @Test
    void addFloats() {
        float a = 10000000000000.0f;
        float b = 1.58f;
        float c = 10000000000000.0f;

        float result = a + b - c;
        System.out.printf("%f\n", result);
        Offset<Float> offset = Offset.offset(0.0001f);
        assertThat(result).isCloseTo(b, offset);
    }

    @Test
    void addDoubles() {
        double a = 10000000000000.0;
        double b = 1.58;
        double c = 10000000000000.0;

        double result = a + b - c;
        System.out.printf("%f\n", result);
        Offset<Double> offset = Offset.offset(0.0001);
        assertThat(result).isCloseTo(b, offset);
    }

    @ParameterizedTest
    @ValueSource(ints = {8, 9, 10, 11, 13})
    void addDoublesBigScaleExperiment(Integer scale) {
        double a = new BigDecimal("10").scaleByPowerOfTen(scale).doubleValue();
        double b = 1.58;
        double c = new BigDecimal("10").scaleByPowerOfTen(scale).doubleValue();
        System.out.printf("%f\n", a);

        double result = a + b - c;
        System.out.printf("%f\n", result);
        Offset<Double> offset = Offset.offset(0.0001);
        assertThat(result).isCloseTo(b, offset);
    }


    @Test
    void addLongs() {
        long a = 1000000000000000L;
        long b = 158L;
        long c = 1000000000000000L;

        long result = a + b - c;
        System.out.printf("%d\n", result);
        assertThat(result).isEqualTo(b);
    }

    @Test
    void addBigDecimals() {
        BigDecimal a = new BigDecimal("10000000000000.0");
        BigDecimal b = new BigDecimal("1.58");
        BigDecimal c = new BigDecimal("10000000000000.0");

        BigDecimal result = a.add(b).subtract(c);
        System.out.printf("%f\n", result);
        assertThat(result).isEqualTo(b);
    }

    @Test
    void addLongsInALoop() {
        long base = 158L;
        int loopCount = 100_000;
        long result = 0;
        for (int i = 0; i < loopCount; i++) {
            result += base;
        }
        System.out.printf("%d\n", result);
        assertThat(result).isEqualTo(15_800_000);
    }

    @Test
    void addFloatsInALoop() {
        float base = 1.58f;
        int loopCount = 100_000;
        float result = 0;
        for (int i = 0; i < loopCount; i++) {
            result += base;
        }
        System.out.printf("%f\n", result);
        Offset<Float> offset = Offset.offset(0.0001f);
        assertThat(result).isCloseTo(158_000.00f, offset);
    }

    @Test
    void addDoublesInALoop() {
        double base = 1.58;
        int loopCount = 100_000;
        double result = 0;
        for (int i = 0; i < loopCount; i++) {
            result += base;
        }
        System.out.printf("%f\n", result);
        Offset<Double> offset = Offset.offset(0.0001d);
        assertThat(result).isCloseTo(158_000.00, offset);
    }

    @Test
    void addBigDecimalsInALoop() {
        BigDecimal base = new BigDecimal("1.58");
        int loopCount = 100_000;
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < loopCount; i++) {
            result = result.add(base);
        }
        System.out.printf("%f\n", result);
        assertThat(result).isEqualTo(new BigDecimal("158000.00"));
    }

    @Test
    void addBigDecimalsMixedInALoop() {
        BigDecimal base = new BigDecimal("0.01");
        BigDecimal base2 = new BigDecimal("10000.00");
        int loopCount = 100_000;
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < loopCount; i++) {
            result = result.add(base);
            result = result.add(base2);
        }
        System.out.printf("%f\n", result);
        assertThat(result).isEqualTo(new BigDecimal("1000001000.00"));
    }

    @Test
    void addDoublesInALoopMixed() {
        double base = 0.01;
        double base2 = 10000.00;
        int loopCount = 100_000;
        double result = 0;
        for (int i = 0; i < loopCount; i++) {
            result += base;
            result += base2;
        }
        System.out.printf("%f\n", result);
        System.out.printf("%f\n", 1000001000.00);
        Offset<Double> offset = Offset.offset(0.0001);
        assertThat(result).isCloseTo(1000001000.00, offset);
    }

    @Test
        //This works?
    void multiplyDoublesInALoopMixed() {
        double base = 0.01;
        double base2 = 10.00;
        double vatRate = 1.23;
        int loopCount = 10;
        double result = 0;
        for (int i = 0; i < loopCount; i++) {
            result += base * vatRate;
            result += base2 * vatRate;
        }
        System.out.printf("%f\n", result);
        Offset<Double> offset = Offset.offset(0.0001d);
        assertThat(result).isCloseTo(123.123, offset);
    }

    @Test
    void multiplyDoublesInALoopMixed2() {
        double base = 0.01;
        double base2 = 10000.00;
        double vatRate = 1.23;
        int loopCount = 10;
        double result = 0;
        for (int i = 0; i < loopCount; i++) {
            result += base * vatRate;
            result += base2 * vatRate;
        }
        System.out.printf("%f\n", result);
        Offset<Double> offset = Offset.offset(0.0001d);
        assertThat(result).isCloseTo(123000.123, offset);
    }

    @Test
    void multiplyBigDecimalsInALoop() {
        BigDecimal base = new BigDecimal("0.01");
        BigDecimal base2 = new BigDecimal("10000.00");
        BigDecimal vatRate = new BigDecimal("1.23");
        int loopCount = 10;
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < loopCount; i++) {
            result = result.add(base.multiply(vatRate));
            result = result.add(base2.multiply(vatRate));
        }
        System.out.printf("%f\n", result);
        assertThat(result).isEqualTo(new BigDecimal("123000.1230"));
    }

}
