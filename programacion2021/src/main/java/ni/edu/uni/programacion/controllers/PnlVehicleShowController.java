/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ni.edu.uni.programacion.controllers;

import com.google.gson.Gson;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import ni.edu.uni.programacion.backend.dao.implementation.JsonVehicleDaoImpl;
import ni.edu.uni.programacion.backend.pojo.Vehicle;
import ni.edu.uni.programacion.backend.pojo.VehicleSubModel;
import ni.edu.uni.programacion.views.panels.PnlVehicleShowInfo;

/**
 *
 * @author Hamlet
 */
public class PnlVehicleShowController {
    private PnlVehicleShowInfo pnlVShowInfo;
    // para el cmbList
    private final String PROPIERTIES[] = new String[]{"Nº Record","Stock number","Year", "Make", "Model", "Style", "VIN", "Exterior color", "Interior color", "Miles", "Price", "Transmission", "Engine",
    "IMAGE","STATUS"};
    private DefaultTableModel model;
    private Gson gson;
    private JsonVehicleDaoImpl jvdao;
    private List<Vehicle> list= new ArrayList<Vehicle>();
    
    
    public PnlVehicleShowController(PnlVehicleShowInfo pnlVShowInfo) throws IOException {
        this.pnlVShowInfo = pnlVShowInfo;
        initComponent();
       
    }
    
    private void initComponent() throws FileNotFoundException, IOException 
    {
        model = (DefaultTableModel) pnlVShowInfo.getTableInfo().getModel();
        jvdao = new JsonVehicleDaoImpl();
        
        pnlVShowInfo.getBtnShowAll().addActionListener((e) ->{
            try {
                btnShowAllActionListener(e);
            } catch (IOException ex) {
                Logger.getLogger(PnlVehicleShowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        // Obtener info desde el jtextfield
        pnlVShowInfo.getTextSearch().addKeyListener(new KeyAdapter()
        {
            public void keyReleased(final KeyEvent e)
            {
                TableRowSorter TFilter = new TableRowSorter(pnlVShowInfo.getTableInfo().getModel());
                String s = pnlVShowInfo.getTextSearch().getText();
                
                pnlVShowInfo.getTextSearch().setText(s);
                
                FilterTabe(pnlVShowInfo.getCmbSearch().getSelectedIndex(), TFilter);
            }
        });
    }
    
    
    // Funcion filtrar
    private void FilterTabe(int a, TableRowSorter filter)
    {
        filter.setRowFilter(RowFilter.regexFilter(pnlVShowInfo.getTextSearch().getText(), a));
        pnlVShowInfo.getTableInfo().setRowSorter(filter);
    } 
    
    // btn ShowAllActionListener
    private void btnShowAllActionListener(ActionEvent e) throws IOException
    {
        // Creamos una lista con los objetos json casteandolos a lista tipo vehicle
        list = (List<Vehicle>) jvdao.getAll();
        while (list.size() > pnlVShowInfo.getTableInfo().getRowCount())
        {
            model.addRow(new Object[]{});
        }

        for (int i = 0; i < list.size(); i++) 
        {
            pnlVShowInfo.getTableInfo().setValueAt((i + 1), i, 0);
            pnlVShowInfo.getTableInfo().setValueAt(list.get(i).getStockNumber(), i, 1);
            pnlVShowInfo.getTableInfo().setValueAt(list.get(i).getYear(), i, 2);
            pnlVShowInfo.getTableInfo().setValueAt(list.get(i).getMake(), i, 3);
            pnlVShowInfo.getTableInfo().setValueAt(list.get(i).getModel(), i, 4);
            pnlVShowInfo.getTableInfo().setValueAt(list.get(i).getStyle(), i, 5);
            pnlVShowInfo.getTableInfo().setValueAt(list.get(i).getVin(), i, 6);
            pnlVShowInfo.getTableInfo().setValueAt(list.get(i).getExteriorColor(), i, 7);
            pnlVShowInfo.getTableInfo().setValueAt(list.get(i).getInteriorColor(), i, 8);
            pnlVShowInfo.getTableInfo().setValueAt(list.get(i).getMiles(), i, 9);
            pnlVShowInfo.getTableInfo().setValueAt(list.get(i).getPrice(), i, 10);
            pnlVShowInfo.getTableInfo().setValueAt(list.get(i).getTransmission().toString(), i, 11);
            pnlVShowInfo.getTableInfo().setValueAt(list.get(i).getEngine(), i, 12);
            pnlVShowInfo.getTableInfo().setValueAt(list.get(i).getImage(), i, 13);
            pnlVShowInfo.getTableInfo().setValueAt(list.get(i).getStatus(), i, 14);
        }
        pnlVShowInfo.getBtnShowAll().setText("Mostrar Todo");
    }
}
