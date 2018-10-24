package com.example.legia.mobileweb;

import com.bitpay.sdk.model.Invoice;
import com.example.legia.mobileweb.TyGia.DocTyGia;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testTiGia() {
        assertEquals(23375, DocTyGia.giaBan(), 0);
    }

    @Test
    public void testBitcoin(){

    }


}