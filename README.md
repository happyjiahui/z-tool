# z-tool

* fillExcelByTemplate 使用说明：

    excel模板分为两部分：
    
    1. 需要全局替换的字符串，在excel模板中用 $key$ 表示，直接在单元格中声明模板；
    
        例如: 公司周报 $startDate$ -- $endDate$ 编写人: $username$
        
    1. 表格数据，需先在excel定义模板行，模板的信息用json表示，在单元格的批注声明，并需要模板行定义好格式，数据会按照模板行单元格指定的格式进行填充，注意模板行的首列必须声明，如没有声明则不认为这行为模板行，一个excel可以声明多个模板行;
        
        模板的json信息格式如下：
        
        ```java
        /**
         * 数据标识
         */
        private String dataFlag;
        /**
         * 字段名称
         */
        private String fieldName;
        /**
         * 合并字段名称
         */
        private String mergedFieldName;
        /**
         * 后并后是否需要合计
         */
        private boolean sum;
        /**
         * 合并后是否需要重新编号
         */
        private boolean autoNumber;
        ```

