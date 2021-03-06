/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikacja;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import pomoc.AnswerList;
import pomoc.Histogram;
import pomoc.Score;
import pomoc.Template;

/**
 *
 * @author Marcin
 */
public class InterfaceM extends javax.swing.JFrame {

    private Template template;
    private AnswerList answerList;
    private List<Histogram> histograms = new ArrayList();
    Score sc=new Score();
    private int w;
    private int k;
    /**
     * Creates new form Glowna
     */
    public InterfaceM() {
        initComponents();
        jTable.setVisible(false);
        jTable.setEnabled(false);
        ImageIcon img=new ImageIcon("C:\\Users\\Marcin\\Documents\\coffe.png");
        this.setIconImage(img.getImage());
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar2 = new javax.swing.JScrollBar();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Wczytaj Szablon");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(textArea);

        jButton2.setText("Wczytaj zbiór kart odpowiedzi");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTable);

        jButton3.setLabel("Wygeneruj statystyki");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 185, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    
    JFileChooser fc = new JFileChooser();
    if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
    {
        textArea.setText("");
        File file = fc.getSelectedFile();
        template = new Template(file.getAbsolutePath());
    }
    textArea.append(template.toString());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JFileChooser fc = new JFileChooser();
        if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
        {
            File file = fc.getSelectedFile();
            answerList = new AnswerList(file.getAbsolutePath());
        }
        
        DefaultTableModel model = new DefaultTableModel();
        
        model.setColumnIdentifiers(answerList.getAnswerList().get(0));
        
        jTable.setModel(model);
        w=answerList.getAnswerList().get(0).length;
        k=answerList.getAnswerList().size();
        Object[]data=new Object[w];
        for(int i=1;i<k;i++)
        {
            data=answerList.getAnswerList().get(i);
            model.addRow(data);
        }
        
        jTable.setVisible(true);
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int[] distribution = new int[w-3];
        for(int x=0;x<w-3;x++)
        {
            distribution[x]=0;
        }
        for(int i=1;i<k;i++)
        {
            int j;
            float score=0;
            for(j=3;j<w;j++)
            {
                if(answerList.getAnswerList().get(i)[j].equals(template.getQuestions().get(j-3).getTrueAnswer())==true)
                {
                    score++;  
                    distribution[j-3]++;
                }
            }
            score=score/(j-3)*100;
            Histogram histogram = new Histogram(answerList.getAnswerList().get(i)[2],score);
            histograms.add(histogram);
            if(score<50&&score>=0) sc.inkNdst();
            else if(score>=50 && score<70) sc.inkDst();
            else if(score>=70 && score <85) sc.inkDb();
            else if(score>=85&&score<95) sc.inkBdb();
            else if(score>=95&&score<=100)sc.inkCel();
            else{
                System.out.println(score);
                System.out.println("\nWystapil blad przy wyliczaniu oceny\n");
            }            
        }
        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        for(Histogram h : histograms)
        {
            dcd.setValue(h.getScore(),h.getIndeks(),"");
        }
        JFreeChart jchart = ChartFactory.createBarChart("Histogram zdobytych punktow", "Indeks studenta", "Wynik punktowy[%]", dcd, PlotOrientation.VERTICAL, rootPaneCheckingEnabled, rootPaneCheckingEnabled, rootPaneCheckingEnabled);
        CategoryPlot plot = jchart.getCategoryPlot();
        plot.setRangeGridlinePaint(Color.GREEN);      
        ChartFrame chartFrm = new ChartFrame ("Wyniki według studentów",jchart,true);
        chartFrm.setVisible(true);
        chartFrm.setSize(700,600);
        
        DefaultCategoryDataset dcd2 = new DefaultCategoryDataset();
        dcd2.setValue(sc.getNdst(),"niedostateczny","");
        dcd2.setValue(sc.getDst(),"dostateczny","");
        dcd2.setValue(sc.getDb(),"dobry","");
        dcd2.setValue(sc.getBdb(),"bardzo dobry","");
        dcd2.setValue(sc.getCel(),"celujacy","");
        
        JFreeChart jchart2 = ChartFactory.createBarChart("Histogram otrzymanych ocen", "Ocena", "Liczba poszczegolnych ocen", dcd2, PlotOrientation.VERTICAL, rootPaneCheckingEnabled, rootPaneCheckingEnabled, rootPaneCheckingEnabled);
        CategoryPlot plot2 = jchart2.getCategoryPlot();
        plot2.setRangeGridlinePaint(Color.GREEN);      
        ChartFrame chartFrm2 = new ChartFrame ("Wyniki według ocen",jchart2,true);
        chartFrm2.setVisible(true);
        chartFrm2.setSize(700,600);
        
        DefaultCategoryDataset dcd3 = new DefaultCategoryDataset();
        for(int i=0;i<w-3;i++)
        {
            dcd3.setValue(distribution[i],Integer.toString(i+1),"");
        }
        
        JFreeChart jchart3 = ChartFactory.createBarChart("Histogram prawidłowych odpowiedzi", "Odpowiedź", "Ilosc prawidlowych odpowiedzi", dcd3, PlotOrientation.VERTICAL, rootPaneCheckingEnabled, rootPaneCheckingEnabled, rootPaneCheckingEnabled);
        CategoryPlot plot3 = jchart3.getCategoryPlot();
        plot3.setRangeGridlinePaint(Color.GREEN);      
        ChartFrame chartFrm3 = new ChartFrame ("Wyniki według pytan",jchart3,true);
        chartFrm3.setVisible(true);
        chartFrm3.setSize(700,600);
        
        
    }//GEN-LAST:event_jButton3ActionPerformed
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfaceM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new InterfaceM().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollBar jScrollBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables

}
