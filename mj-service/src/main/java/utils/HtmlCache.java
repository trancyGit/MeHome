package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by renhui on 2017/3/17.
 */
public class HtmlCache{
    public static List<String> cache = new ArrayList<String>();
    public  static void refresh(List<String> list){
        if(null!=list&&list.size()>0){
            cache.clear();
            cache.addAll(list);
        }
    }
}

