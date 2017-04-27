package utils;


/**
 * Created by dell on 2015/12/11.
 */
public class PageMysqlUtil {
    private int pageNow = 1;
    private int pageSize = 20;

    public int getPageNow() {
        return (pageNow - 1) * pageSize;
    }

    public void setPageNow(int pageNow) {
        if (pageNow <= 0) {
            this.pageNow = 1;
        } else {
            this.pageNow = pageNow;
        }
    }

    public int getPageSize() {
        return pageSize <= 0 ? 20 : pageSize;
    }

    public int getSourcePageSize() {
        return pageSize;
    }

    public int getSourcePageNow() {
        return pageNow;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "";
    }
}
