package com.yipeng.imagecut;

import java.util.Date;
import java.util.List;

/**
 * Created by Yipeng on 2016/8/26 0026.
 */
public class aaaa {
    private String reason;
    private int error_code;
    private data result;

    @Override
    public String toString() {
        return "aaaa{" +
                "reason='" + reason + '\'' +
                ", error_code=" + error_code +
                ", result=" + result +
                '}';
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public data getResult() {
        return result;
    }

    public void setResult(data result) {
        this.result = result;
    }

    class data{
        private List<data2> data;

        public List<data2> getData() {
            return data;
        }

        public void setData(List<data2> data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "data{" +
                    "data=" + data +
                    '}';
        }
    }
    class data2{
        private String title;
        private String code;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return "data2{" +
                    "title='" + title + '\'' +
                    ", code='" + code + '\'' +
                    '}';
        }
    }
}
