package com.zxtech.ecs.ui.home.qmsmanager.bi;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.zxtech.ecs.BaseActivity;
import com.zxtech.ecs.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import butterknife.BindView;

/**
 * Created by syp521 on 2018/4/14.
 */

public class QmsBiActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qms_bi;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initTitleBar(toolbar);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            InputStream is = getAssets().open("file.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("struct");
            listItems = new ArrayList<>();

            for (int i = 0; i < nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element1 = (Element) node;

                    ListItem listItem = new ListItem(getValue("head", element1), getValue("par1", element1), getValue("par2", element1), i,
                            Double.parseDouble(getValue("par1val", element1)), Double.parseDouble(getValue("par2val", element1)),
                            Double.parseDouble(getValue("y2014", element1)),
                            Double.parseDouble(getValue("y2015", element1)),
                            Double.parseDouble(getValue("y2016", element1)),
                            Double.parseDouble(getValue("y2017", element1)),
                            Double.parseDouble(getValue("y2018", element1)),
                            Double.parseDouble(getValue("jan15", element1)),
                            Double.parseDouble(getValue("feb15", element1)),
                            Double.parseDouble(getValue("mar15", element1)),
                            Double.parseDouble(getValue("apr15", element1)),
                            Double.parseDouble(getValue("may15", element1)),
                            Double.parseDouble(getValue("jun15", element1)),
                            Double.parseDouble(getValue("jul15", element1)),
                            Double.parseDouble(getValue("aug15", element1)),
                            Double.parseDouble(getValue("sep15", element1)),
                            Double.parseDouble(getValue("oct15", element1)),
                            Double.parseDouble(getValue("nov15", element1)),
                            Double.parseDouble(getValue("dec15", element1)),
                            Double.parseDouble(getValue("jan16", element1)),
                            Double.parseDouble(getValue("feb16", element1)),
                            Double.parseDouble(getValue("mar16", element1)),
                            Double.parseDouble(getValue("apr16", element1)),
                            Double.parseDouble(getValue("may16", element1)),
                            Double.parseDouble(getValue("jun16", element1)),
                            Double.parseDouble(getValue("jul16", element1)),
                            Double.parseDouble(getValue("aug16", element1)),
                            Double.parseDouble(getValue("sep16", element1)),
                            Double.parseDouble(getValue("oct16", element1)),
                            Double.parseDouble(getValue("nov16", element1)),
                            Double.parseDouble(getValue("dec16", element1)),
                            Double.parseDouble(getValue("jan17", element1)),
                            Double.parseDouble(getValue("feb17", element1)),
                            Double.parseDouble(getValue("mar17", element1)),
                            Double.parseDouble(getValue("apr17", element1)),
                            Double.parseDouble(getValue("may17", element1)),
                            Double.parseDouble(getValue("jun17", element1)),
                            Double.parseDouble(getValue("jul17", element1)),
                            Double.parseDouble(getValue("aug17", element1)),
                            Double.parseDouble(getValue("sep17", element1)),
                            Double.parseDouble(getValue("oct17", element1)),
                            Double.parseDouble(getValue("nov17", element1)),
                            Double.parseDouble(getValue("dec17", element1)),
                            Double.parseDouble(getValue("jan18", element1)),
                            Double.parseDouble(getValue("feb18", element1)),
                            Double.parseDouble(getValue("mar18", element1)),
                            Double.parseDouble(getValue("apr18", element1)),
                            Double.parseDouble(getValue("may18", element1)),
                            Double.parseDouble(getValue("jun18", element1)),
                            Double.parseDouble(getValue("jul18", element1)),
                            Double.parseDouble(getValue("aug18", element1)),
                            Double.parseDouble(getValue("sep18", element1)),
                            Double.parseDouble(getValue("oct18", element1)),
                            Double.parseDouble(getValue("nov18", element1)),
                            Double.parseDouble(getValue("dec18", element1)));


                    listItems.add(listItem);


                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        for (int i = 0; i <= 10; i++) {

        }

        adapter = new MyAdapter(listItems, this);

        recyclerView.setAdapter(adapter);
    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}
