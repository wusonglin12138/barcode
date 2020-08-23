package barcode;

import java.io.UnsupportedEncodingException;

import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.Native;

public class TscMain {
    String[] titles = {"客户", "采购单号", "成分", "品名", "幅宽", "颜色/花型"};
    public interface TscLibDll extends StdCallLibrary {
        TscLibDll INSTANCE = (TscLibDll) Native.loadLibrary("TSCLIB", TscLibDll.class);

        int about();

        int openport(String pirnterName);

        int closeport();

        int sendcommand(String printerCommand);

        int setup(String width, String height, String speed, String density, String sensor, String vertical, String offset);

        int downloadpcx(String filename, String image_name);

        int barcode(String x, String y, String type, String height, String readable, String rotation, String narrow, String wide, String code);

        int printerfont(String x, String y, String fonttype, String rotation, String xmul, String ymul, String text);

        int clearbuffer();

        int printlabel(String set, String copy);

        int formfeed();

        int nobackfeed();

        int windowsfont(int x, int y, int fontheight, int rotation, int fontstyle, int fontunderline, String szFaceName, String content);
    }

    public void printBarCode(String customer, String orderNumber, String constituent, String name, String width,
                                    String color, String cylinderNumber, int reelNumber) {
        System.setProperty("jna.encoding", "GBK");// 支持中文

        TscLibDll.INSTANCE.openport("Gprinter  GP-3120TL");
        TscLibDll.INSTANCE.sendcommand("CODEPAGE UTF-8");
        TscLibDll.INSTANCE.sendcommand("SIZE 75 mm, 90 mm");
//        TscLibDll.INSTANCE.sendcommand("GAP 4 mm, 0 mm");
        TscLibDll.INSTANCE.sendcommand("DIRECTION 1");
        TscLibDll.INSTANCE.sendcommand("CLS");

        //外部框
        TscLibDll.INSTANCE.sendcommand("BOX 8,8,592,712,4");
//        TscLibDll.INSTANCE.sendcommand("BOX 0,0,600,720,4");

        //内部横线
        for(int i=1; i<9; i++) {
            TscLibDll.INSTANCE.sendcommand("BAR 8," + Integer.toString(i * 8 * 8) + ",584,4");
        }

        //左侧标签
        for(int j=0; j<titles.length; j++) {
//            TscLibDll.INSTANCE.sendcommand("TEXT 32," + Integer.toString(32 + j * 8 * 8) + ",\"2\",0,1,1, \"" + titles[j] + "\"");
            TscLibDll.INSTANCE.windowsfont(32, 32 + j * 8 * 8, 30, 0, 2, 0, "Arial", titles[j]);
        }

        //左侧竖线
        TscLibDll.INSTANCE.sendcommand("BAR 200,8,4,376");

        //右侧输出值
//        TscLibDll.INSTANCE.sendcommand("TEXT 248," + Integer.toString(32 + 0 * 8 * 8) + ",\"2\",0,1,1, \"" + customer + "\"");
        TscLibDll.INSTANCE.windowsfont(248, 32 + 0 * 8 * 8, 30, 0, 2, 0, "Arial", customer);
        TscLibDll.INSTANCE.sendcommand("TEXT 248," + Integer.toString(32 + 1 * 8 * 8) + ",\"2\",0,1,1, \"" + orderNumber + "\"");
        TscLibDll.INSTANCE.sendcommand("TEXT 248," + Integer.toString(32 + 2 * 8 * 8) + ",\"2\",0,1,1, \"" + constituent + "\"");
        TscLibDll.INSTANCE.sendcommand("TEXT 248," + Integer.toString(32 + 3 * 8 * 8) + ",\"2\",0,1,1, \"" + name + "\"");
        TscLibDll.INSTANCE.sendcommand("TEXT 248," + Integer.toString(32 + 4 * 8 * 8) + ",\"2\",0,1,1, \"" + width + "\"");
        TscLibDll.INSTANCE.sendcommand("TEXT 248," + Integer.toString(32 + 5 * 8 * 8) + ",\"2\",0,1,1, \"" + color + "\"");

        //缸号+公斤标签
//        TscLibDll.INSTANCE.sendcommand("TEXT 32," + Integer.toString(8 + 6 * 8 * 8) + ",\"2\",0,1,1, \"缸号\"");
//        TscLibDll.INSTANCE.sendcommand("TEXT 32," + Integer.toString(32 + 7 * 8 * 8) + ",\"2\",0,1,1, \"公斤数\"");
        TscLibDll.INSTANCE.windowsfont(32, 8 + 6 * 8 * 8, 30, 0, 2, 0, "Arial", "缸号");
        TscLibDll.INSTANCE.windowsfont(32, 32 + 7 * 8 * 8, 30, 0, 2, 0, "Arial", "公斤数");

        //缸号+公斤数输出值
        TscLibDll.INSTANCE.sendcommand("TEXT 32," + Integer.toString(40 + 6 * 8 * 8) + ",\"2\",0,1,1, \"" + cylinderNumber + "\"");

        //最后两行的竖线
        TscLibDll.INSTANCE.sendcommand("BAR 300,384,4,128");

        //卷号+码数标签
//        TscLibDll.INSTANCE.sendcommand("TEXT 316," + Integer.toString(6 * 8 * 8 + 8) + ",\"2\",0,1,1, \"卷号\"");
//        TscLibDll.INSTANCE.sendcommand("TEXT 316," + Integer.toString(7 * 8 * 8 + 32) + ",\"2\",0,1,1, \"码数\"");
        TscLibDll.INSTANCE.windowsfont(316, 6 * 8 * 8 + 8, 30, 0, 2, 0, "Arial", "卷号");
        TscLibDll.INSTANCE.windowsfont(316, 7 * 8 * 8 + 32, 30, 0, 2, 0, "Arial", "码数");

        //卷号输出值
        TscLibDll.INSTANCE.sendcommand("TEXT 316," + Integer.toString(6 * 8 * 8 + 40) + ",\"2\",0,1,1, \"" + Integer.toString(reelNumber) + "\"");

        //条码信息
        String code_message = cylinderNumber + "-" + Integer.toString(reelNumber);
//        TscLibDll.INSTANCE.barcode("120", "520", "128", "120", "2", "0", "2", "2", code_message);
        TscLibDll.INSTANCE.barcode("120", "536", "128", "120", "2", "0", "2", "2", code_message);

        TscLibDll.INSTANCE.printlabel("1", "1");
        TscLibDll.INSTANCE.closeport();
    }

//    public static void main(String[] args) throws UnsupportedEncodingException {
//        System.setProperty("jna.encoding", "GBK");// 支持中文
//        // TscLibDll.INSTANCE.about();
////        TscLibDll.INSTANCE.openport("Gprinter  GP-3120TL");
////        // TscLibDll.INSTANCE.downloadpcx("C:\\UL.PCX", "UL.PCX");
////        // TscLibDll.INSTANCE.sendcommand("REM ***** This is a test by JAVA. *****");
////        TscLibDll.INSTANCE.setup("60", "40", "5", "15", "0", "2", "0");
////
////        TscLibDll.INSTANCE.sendcommand("SET TEAR ON");
////        TscLibDll.INSTANCE.clearbuffer();
////
////        String command = "QRCODE 300,70,L,6,A,0,M2,S3,\"123456\"";// 打印二维码
////        TscLibDll.INSTANCE.sendcommand(command);
////        // TscLibDll.INSTANCE.sendcommand("PUTPCX 550,10,\"UL.PCX\"");
////
////        // TscLibDll.INSTANCE.printerfont("100", "50", "TSS24.BF2", "0", "1", "1", "Technology");
////        TscLibDll.INSTANCE.barcode("70", "140", "128", "90", "0", "0", "2", "2", "A123456789");// 打印内容，参数是位置和字体
////        TscLibDll.INSTANCE.windowsfont(15, 15, 40, 0, 2, 1, "Arial", "网络科技公司");
////        TscLibDll.INSTANCE.windowsfont(30, 90, 32, 0, 2, 0, "Arial", "--- 研发部");
////        TscLibDll.INSTANCE.windowsfont(120, 240, 32, 0, 2, 0, "Arial", "A123456789");
////        TscLibDll.INSTANCE.printlabel("1", "1");
////        TscLibDll.INSTANCE.closeport();
//
//
////        TscLibDll.INSTANCE.openport("Gprinter  GP-3120TL");
////        TscLibDll.INSTANCE.setup("60", "40", "5", "10", "0", "2", "0");
////        TscLibDll.INSTANCE.sendcommand("SET TEAR ON");
////        TscLibDll.INSTANCE.clearbuffer();
////        TscLibDll.INSTANCE.sendcommand("QRCODE 100,200,L,7,M,0,[2,7],'N123456' ");
////        // TscLibDll.INSTANCE.sendcommand("PUTPCX 550,10,\"UL.PCX\"");
////        TscLibDll.INSTANCE.barcode("70", "140", "128", "90", "0", "0", "2", "2", "code");
////        TscLibDll.INSTANCE.windowsfont(55, 20, 48, 0, 2, 1, "Arial", "中文");
////        TscLibDll.INSTANCE.windowsfont(120, 240, 32, 0, 2, 0, "Arial", "code");
////        // TscLibDll.INSTANCE.windowsfont(400, 200, 48, 90, 3, 1, "Arial", "DEG 90");
////        // TscLibDll.INSTANCE.windowsfont(400, 200, 48, 180, 3, 1, "Arial", "DEG 180");
////        // TscLibDll.INSTANCE.windowsfont(400, 200, 48, 270, 3, 1, "Arial", "DEG 270");
////        TscLibDll.INSTANCE.printlabel("1", "1");
////        TscLibDll.INSTANCE.closeport();
//
//
////        TscLibDll.INSTANCE.openport("Gprinter  GP-3120TL");
////        TscLibDll.INSTANCE.sendcommand("CODEPAGE UTF-8");
////        TscLibDll.INSTANCE.sendcommand("SIZE 75 mm, 90 mm");
////        TscLibDll.INSTANCE.sendcommand("GAP 2 mm, 0 mm");
////        TscLibDll.INSTANCE.sendcommand("DIRECTION 1");
////        TscLibDll.INSTANCE.sendcommand("CLS");
////
////        TscLibDll.INSTANCE.sendcommand("BOX 8,8,592,712,4");
////        TscLibDll.INSTANCE.printlabel("1", "1");
////        TscLibDll.INSTANCE.closeport();
//    }
}