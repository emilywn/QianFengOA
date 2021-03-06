package oa.qianfeng.com.oa.utils;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import oa.qianfeng.com.oa.entity.KaoQinAllBean;
import oa.qianfeng.com.oa.entity.KaoQinBean;

/**
 * Created by Administrator on 2016/10/16 0016.
 */
public class HtmlUtils {

    /**
     * 解析html，得出考勤集合
     *
     * @param html
     * @return
     */
    public static List<KaoQinBean> getKaoQinBeanByHtml(String html) {
        ArrayList<KaoQinBean> beans = new ArrayList<>();
        Document value = Jsoup.parseBodyFragment(html);
        Log.d("ytmfdw", "parseBody valueSize=");
        Elements es = value.getElementsByClass("table-responsive");
        if (es.size() == 2) {
            //合格，第一个是考勤汇总，第二个是个人考勤详情
            Element e_all = es.get(0);
            Elements e_all_body = e_all.getElementsByTag("tr");
            Elements e_all_value = e_all_body.get(2).getElementsByTag("td");
            StringBuilder sb_all = new StringBuilder();
            for (Element e : e_all_value) {
                sb_all.append(e.text()).append("\t");
            }
            Log.d("ytmfdw", "e_all.toString=" + sb_all.toString());
            //解析打卡详情=============================================
            Element e_kq = es.get(1);
            Element e_kq_value = e_kq.getElementsByTag("tbody").get(0);
            Elements e_tmp = e_kq_value.getElementsByTag("tr");
            for (Element e : e_tmp) {
                StringBuilder sb_tmp = new StringBuilder();
                Elements tds = e.getElementsByTag("td");
                if (tds != null && tds.size() == 7) {
                    KaoQinBean bean = new KaoQinBean(tds);
                    beans.add(bean);
                }
            }
            L.d("ytmfdw", "e_kq.toString=" + beans.toString());
        }
        //反序
//        Collections.reverse(beans);
        return beans;
    }


    /**
     * 解析xml，得出考勤汇总数据
     *
     * @param html
     * @return
     */
    public static KaoQinAllBean getKaoQinAllBeanByHtml(String html) {
        ArrayList<KaoQinAllBean> beans = new ArrayList<>();
        Document value = Jsoup.parseBodyFragment(html);
        Log.d("ytmfdw", "parseBody valueSize=");
        Elements es = value.getElementsByClass("table-responsive");
        if (es.size() == 2) {
            //合格，第一个是考勤汇总，第二个是个人考勤详情
            Element e_all = es.get(0);
            Elements e_all_body = e_all.getElementsByTag("tr");
            Elements e_all_value = e_all_body.get(2).getElementsByTag("td");
            return new KaoQinAllBean(e_all_value);
        }
        return null;
    }

    /**
     * 提取字符串中的所有中文字符
     *
     * @param value
     * @return
     */
    public static String getChinese(String value) {
        L.d("登录结果：value=" + value);
        String reg = "[^\u4e00-\u9fa5]";
        String data = value.replaceAll(reg, "");
        L.d("登录结果：value=" + data);
        return data;
    }
}
