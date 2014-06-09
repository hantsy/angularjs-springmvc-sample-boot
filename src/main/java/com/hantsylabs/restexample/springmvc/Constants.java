package com.hantsylabs.restexample.springmvc;

public class Constants {

    /**
     * prefix of REST API
     */
    public static final String URI_API = "/api";

    /**
     * Definitions of defect types.
     *
     */
    public static final int[] DEFECT_TYPES = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    /**
     * excludes 0, 8 and 11
     */
    public static final int[] DEFECT_TYPES_LIMITED = {1, 2, 3, 4, 5, 6, 7, 9, 10, 12, 13};

    public static final String[] GRADE_NAMES = {"A1", "A2", "A3", "A4", "A5", "A6"};

    public static final String PROP_IMAGE_PATH = "image.path";

    public static final String PROP_CACHE_PATH = "cache.path";

    public static final String PROP_DATA_PATH = "data.path";

    public static String[] DEFECT_TYPE_NAMES = {
        "未定义（0）",
        "污损（1）",
        "纬破（2）",
        "并纬（3）",
        "经破（4）",
        "经污（5）",
        "孔洞（6）",
        "折痕（7）",
        "毛羽（8）",
        "漏丝（9）",
        "裁边不良（10）",
        "毛羽扩展（11）",
        "纬结（12）",
        "接头（13）"
    };

}
