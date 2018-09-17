package com.mycompany.arduinoaula1;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

public class App {

    static List<Double> leituras = new ArrayList<>();
    static List<Double> listaTemperatureValues = new ArrayList<>();
    static List<Double> listLuminosidade = new ArrayList<>();
    static JFrame frame = new JFrame("Charts");
    static double i = 0;
    static JFreeChart chart;
    static ChartPanel cp;
    static XYDataset ds;

    static String[] pins = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
    static JComboBox comboListaPins = new JComboBox(pins);
    
    private Javino jBridge = new Javino("/anaconda3/envs/python2710/bin");
    private String porta = "/dev/cu.usbmodem1421";

    public static void main(String[] args) {

//        XYDataset ds = createDataset();
//        chart = ChartFactory.createXYLineChart("Test Chart",
//                "x", "y", ds, PlotOrientation.VERTICAL, true, true,
//                false);
//        cp = new ChartPanel(chart);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        ds = createDataset();
        
        chart = ChartFactory.createXYLineChart("Test Chart",
                "x", "y", ds, PlotOrientation.VERTICAL, true, true,
                false);
        cp = new ChartPanel(chart);
//        frame.setVisible(true);
        frame.getContentPane().add(comboListaPins);
        comboListaPins.setVisible(true);

        UIUpdater t = new UIUpdater();
        t.start();

    }

    private static XYDataset createDataset() {

        listaTemperatureValues.add(generateRandomicValue());
        listLuminosidade.add(generateRandomicValue());
        leituras.add(i++);

        DefaultXYDataset ds = new DefaultXYDataset();
        double[] arrayLeituras = new double[leituras.size()];
        for (int i = 0; i < leituras.size(); i++) {
            arrayLeituras[i] = leituras.get(i);
        }

        double[] arrayTemperaturas = new double[listaTemperatureValues.size()];
        for (int i = 0; i < listaTemperatureValues.size(); i++) {
            arrayTemperaturas[i] = listaTemperatureValues.get(i);
        }

        double[] arrayLuminosidade = new double[listLuminosidade.size()];
        for (int i = 0; i < listLuminosidade.size(); i++) {
            arrayLuminosidade[i] = listLuminosidade.get(i);
        }

        double[][] serieTemperatura = {arrayLeituras, arrayTemperaturas};

        double[][] serieLuminosidade = {arrayLeituras, arrayLuminosidade};

        ds.addSeries("Temperaturas", serieTemperatura);
        ds.addSeries("Luminosidade", serieLuminosidade);

        return ds;
    }

    private static double generateRandomicValue() {
        double value = Math.random();
        return value;
    }

    static class UIUpdater extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
//                    frame.setSize(600, 400);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                    frame.setVisible(true);

                    ds = createDataset();
                    chart = ChartFactory.createXYLineChart("Aula 1",
                            "x", "y", ds, PlotOrientation.VERTICAL, true, true,
                            false);
                    cp.setChart(chart);
                    cp.setSize(400, 300);
                    cp.setDomainZoomable(true);
//                    cp.repaint();

                    cp.setPreferredSize(new java.awt.Dimension(500, 270));

                    frame.getContentPane().add(cp, BorderLayout.CENTER);
                    frame.repaint();
                    frame.setVisible(true);

                } catch (Exception e) {
                    System.out.println("Error: " + e);
                } finally {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

}
