package com.example.legia.mobileweb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.legia.mobileweb.AdapterSanPham.ListViewTyGia;
import com.example.legia.mobileweb.DTO.TyGia;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class tygia extends AppCompatActivity {
    ListView listTyGia;
    List<TyGia> danhSachTyGia = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tygia);

        this.setTitle("Xem tỉ giá online");

        listTyGia = findViewById(R.id.listTyGia);
        ListViewTyGia adapter = null;

        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra("news");
        String urlProvider = b.getString("urlProvider");

        try {
            URL xmlUrl = new URL("https://www.vietcombank.com.vn/ExchangeRates/ExrateXML.aspx");
            InputStream in = xmlUrl.openStream();
            Document doc = parse(in);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Exrate");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                // kiểm tra

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    /*System.out.println("Ký hiệu: " + element.getAttribute("CurrencyCode"));
                    System.out.println("Nước: " + element.getAttribute("CurrencyName"));
                    System.out.println("Giá mua: " + element.getAttribute("Buy"));
                    System.out.println("Giá bán: " + element.getAttribute("Sell"));
                    System.out.println("Giá chuyển: " + element.getAttribute("Transfer"));*/
                    danhSachTyGia.add(new TyGia(element.getAttribute("CurrencyCode"), Double.parseDouble(element.getAttribute("Buy")), Double.parseDouble(element.getAttribute("Sell"))));

                }
            }
            adapter = new ListViewTyGia(this, danhSachTyGia);
            listTyGia.setAdapter(adapter);
        } catch (MalformedURLException ex) {

        } catch (IOException ex) {

        }

    }

    private Document parse (InputStream is) {
        Document ret = null;
        DocumentBuilderFactory domFactory;
        DocumentBuilder builder;

        try {
            domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setValidating(false);
            domFactory.setNamespaceAware(false);
            builder = domFactory.newDocumentBuilder();

            ret = builder.parse(is);
        }
        catch (Exception ex) {
            System.out.println("unable to load XML: " + ex);
        }
        return ret;
    }
}
