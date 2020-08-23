package barcode;

import com.sun.jna.IntegerType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.font.TextAttribute.FONT;

public class BarCode {
    private JTextField customerText;
    private JTextField orderNumberText;
    private JTextField constituentText;
    private JTextField nameText;
    private JTextField widthText;
    private JTextField colorText;
    private JTextField cylinderNumberText;
    private JTextField reelNumberText;

    public void initJFrame() {
        // 创建 JFrame 实例
        JFrame frame = new JFrame("条码打印");
        // Setting the width and height of frame
        frame.setSize(500, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel);

        // 设置界面可见
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        BarCode barCode = new BarCode();
        barCode.initJFrame();
    }

//    public static void main(String[] args) {
//        // 创建 JFrame 实例
//        JFrame frame = new JFrame("条码打印");
//        // Setting the width and height of frame
//        frame.setSize(500, 800);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        /* 创建面板，这个类似于 HTML 的 div 标签
//         * 我们可以创建多个面板并在 JFrame 中指定位置
//         * 面板中我们可以添加文本字段，按钮及其他组件。
//         */
//        JPanel panel = new JPanel();
//        // 添加面板
//        frame.add(panel);
//        /*
//         * 调用用户定义的方法并添加组件到面板
//         */
//        placeComponents(panel);
//
//        // 设置界面可见
//        frame.setVisible(true);
//    }

    private void placeComponents(JPanel panel) {

        panel.setLayout(null);

        // 客户标签
        JLabel customerLabel = new JLabel("客户:");
        /**
         * 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        customerLabel.setBounds(10,20,200,25);
        customerLabel.setFont(new Font("字体", Font.PLAIN, 20));
        panel.add(customerLabel);

        //客户文本域
        customerText = new JTextField(20);
        customerText.setBounds(120,20,165,25);
//        customer = customerText.getText();
        panel.add(customerText);

        // 订单编号标签
        JLabel orderNumberLabel = new JLabel("订单编号:");
        orderNumberLabel.setBounds(10,50,200,25);
        orderNumberLabel.setFont(new Font("字体", Font.PLAIN, 20));
        panel.add(orderNumberLabel);

        //订单编号文本域
        orderNumberText = new JTextField(20);
        orderNumberText.setBounds(120,50,165,25);
//        orderNumber = orderNumberText.getText();
        panel.add(orderNumberText);

        // 成分标签
        JLabel constituentLabel = new JLabel("成分:");
        constituentLabel.setBounds(10,80,200,25);
        constituentLabel.setFont(new Font("字体", Font.PLAIN, 20));
        panel.add(constituentLabel);

        //成分文本域
        constituentText = new JTextField(20);
        constituentText.setBounds(120,80,165,25);
//        constituent = constituentText.getText();
        panel.add(constituentText);

        // 品名标签
        JLabel nameLabel = new JLabel("品名:");
        nameLabel.setBounds(10,110,200,25);
        nameLabel.setFont(new Font("字体", Font.PLAIN, 20));
        panel.add(nameLabel);

        //品名文本域
        nameText = new JTextField(20);
        nameText.setBounds(120,110,165,25);
//        name = nameText.getText();
        panel.add(nameText);

        // 幅宽标签
        JLabel widthLabel = new JLabel("幅宽:");
        widthLabel.setBounds(10,140,200,25);
        widthLabel.setFont(new Font("字体", Font.PLAIN, 20));
        panel.add(widthLabel);

        //幅宽文本域
        widthText = new JTextField(20);
        widthText.setBounds(120,140,165,25);
//        width = widthText.getText();
        panel.add(widthText);

        // 颜色/花型标签
        JLabel colorLabel = new JLabel("颜色/花型:");
        colorLabel.setBounds(10,170,200,25);
        colorLabel.setFont(new Font("字体", Font.PLAIN, 20));
        panel.add(colorLabel);

        // 颜色/花型标签文本域
        colorText = new JTextField(20);
        colorText.setBounds(120,170,165,25);
//        color = colorText.getText();
        panel.add(colorText);

        // 缸号标签
        JLabel cylinderNumberLabel = new JLabel("缸号:");
        cylinderNumberLabel.setBounds(10,200,200,25);
        cylinderNumberLabel.setFont(new Font("字体", Font.PLAIN, 20));
        panel.add(cylinderNumberLabel);

        // 缸号文本域
        cylinderNumberText = new JTextField(20);
        cylinderNumberText.setBounds(120,200,165,25);
//        cylinderNumber = cylinderNumberText.getText();
        panel.add(cylinderNumberText);

        // 卷号上限标签
        JLabel reelNumberLabel = new JLabel("卷号上限:");
        reelNumberLabel.setBounds(10,230,200,25);
        reelNumberLabel.setFont(new Font("字体", Font.PLAIN, 20));
        panel.add(reelNumberLabel);

        // 颜色/花型标签文本域
        reelNumberText = new JTextField(20);
        reelNumberText.setBounds(120,230,165,25);
//        reelNumber = Integer.parseInt(reelNumberText.getText());
        panel.add(reelNumberText);

        // 创建打印按钮
        JButton printButton = new JButton("打印");
        printButton.setBounds(10, 300, 80, 25);
        printButton.setFont(new Font("字体", Font.PLAIN, 20));
        addActionListener(printButton);
        panel.add(printButton);
    }

    private void addActionListener(JButton saveButton) {
        // 为按钮绑定监听器
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("".equals(customerText.getText()) || "".equals(orderNumberText.getText()) || "".equals(constituentText.getText()) ||
                "".equals(nameText.getText()) || "".equals(widthText.getText()) || "".equals(colorText.getText()) ||
                "".equals(cylinderNumberText.getText()) || "".equals(reelNumberText.getText())) {
                    JOptionPane.showMessageDialog(null, "参数为空，请检查输入");
                } else {
                    //打印信息
                    System.out.println("开始打印条码单！");
                    for(int i=1; i<=Integer.parseInt(reelNumberText.getText()); i++){
                        TscMain tscMain = new TscMain();
                        tscMain.printBarCode(customerText.getText(), orderNumberText.getText(), constituentText.getText(), nameText.getText(),
                                widthText.getText(), colorText.getText(), cylinderNumberText.getText(), i);
                    }

                }
            }
        });
    }

}