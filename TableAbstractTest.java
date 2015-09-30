
package com.test;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;




/**
 * 
 * @author root
 */
public class TableAbstractTest extends JFrame 
{
    
 private JTable table ;
 ArrayList data = new ArrayList<>();
  JLabel statusLabel ;   
  TableRowSorter trs ;
 public static void main(String arg[]) 
  {
    
      java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() 
            {
                new TableAbstractTest().setVisible(true);;
                
            }
        });
    
  }
    /**
         * 
         */
  public TableAbstractTest() {
    
     initData();
     buildUI();
  }
 /**
    * 
    */
   private void buildUI()
   {
     
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

   
        TestTableModel model;
        ArrayList<TestColumnField> columnDescriptionData = new ArrayList<>();
        
        TestColumnField tcf = new TestColumnField();
         tcf.setName("id");tcf.setType("num");
         columnDescriptionData.add(tcf);
         tcf = new TestColumnField();
         tcf.setName("name");tcf.setType("text");
         columnDescriptionData.add(tcf);
         tcf = new TestColumnField();
         tcf.setName("value");tcf.setType("text");
         columnDescriptionData.add(tcf);
        
         

         
        model = new TestTableModel(columnDescriptionData,data);
        
      
        //  table.setAutoCreateRowSorter(true);
          trs = new TableRowSorter(model);   
          table = new JTable(model);
          
          trs.setSortsOnUpdates(false);
          table.setRowSorter(trs);
          table.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
           {
                            @Override
                            public void valueChanged(ListSelectionEvent event) 
                            {
                                 if (!event.getValueIsAdjusting()) {
                                dataSelectionListener(event);     
                                 }
                            }
           });
    JScrollPane lScrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                          JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    

    getContentPane().add(lScrollPane, BorderLayout.CENTER);
    JPanel btmPanel = new JPanel(new BorderLayout());    
    JPanel btnPanel    = new JPanel();
    JPanel statusPanel = new JPanel();
    
    btmPanel.add(statusPanel,BorderLayout.NORTH);
    btmPanel.add(btnPanel,   BorderLayout.SOUTH);
    
    
    JButton btn = new JButton();
    statusLabel = new JLabel("ok");
    
    statusPanel.add(statusLabel,BorderLayout.CENTER);
  //  getContentPane().add(statusPanel,BorderLayout.SOUTH);    
    getContentPane().add(btmPanel,BorderLayout.SOUTH);
    
    btn.setText("Add");  
    
    JButton btnPrint = new JButton();  
    btnPrint.setText("Output Data");   
    
    JButton btnRemove = new JButton();  
    btnRemove.setText("Remove");    
    
    
    btnPanel.add(btn,BorderLayout.EAST);
    btnPanel.add(btnPrint,BorderLayout.WEST);
    btnPanel.add(btnRemove,BorderLayout.WEST);
    btnRemove.addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mousePressed(MouseEvent e) 
                    {
                              mouseAction("remove");
                              
                    }
                });
    btn.addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mousePressed(MouseEvent e) 
                    {
                              mouseAction("new");
                    }
                });
    btnPrint.addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mousePressed(MouseEvent e) 
                    {
                              mouseAction("print");
                    }
                });

    pack();
   }
 
  /**
   * 
   * @param msg 
   */ 
   private static void log (String msg)
    {
        System.out.println(msg); 
    }
   /**
    * set def values to tbl
    */
   private void initData()
   {
        for (int y=0;y<4;y++)
    {
        TestRowField row = new TestRowField();
        ArrayList cols = new ArrayList<TestColumnField>();
        
                    for (int x=0;x<3;x++)
                    {

                        TestColumnField tcf = new TestColumnField();
                            if (x==0)
                            {

                            tcf.setName("id");
                            tcf.setType("num"); 
                            tcf.setValue(y);
                            }
                            else if (x==1)
                            {

                            tcf.setName("name");
                            tcf.setType("text"); 
                            tcf.setValue("test row: "+y+", value="+x);
                            }
                            else if (x==2)
                            {

                            tcf.setName("value"+x);
                            tcf.setType("text"); 
                            tcf.setValue("value row: "+y+", value="+x);
                            }
                        cols.add(tcf);
                    }
    row.setCols(cols);
    data.add(row);
   }
   }
   
    /**
     * 
     * @param event 
     */
    public void dataSelectionListener(ListSelectionEvent event)
    {
    
        int x = table.getSelectedRow(), _y=-1;
        log("selected view row:" + x);
        
        if (x!=-1)
        {
            _y = table.convertRowIndexToModel(x);
        
        log("selected model row:" + _y); 
        statusLabel.setText("row selected ("+ x + "), model row: " + _y);
        }
    }
    /**
     * 
     * @param action 
     */
    private void mouseAction(String action)
    {
        TestTableModel ttm = ((TestTableModel)table.getModel());
        if ("remove".equals(action))
        {
            int x = table.getSelectedRow();
            if (x==-1)
            {
                log("row not selected");
                return;
            }
            x = table.convertRowIndexToModel(x); 
            ttm.removeRow(x);

        }
       else if ("print".equals(action))
                {
                   ArrayList<TestRowField> data= (ArrayList<TestRowField>)ttm.getData();
                   int i=0;
                   for (TestRowField row: data)
                   {
                       ArrayList<TestColumnField> cols = row.getCols();
                       log("row: "+i);
                       log("row id: "    + (Integer)cols.get(0).getValue());
                       log("row value: " + (String)cols.get(1).getValue());
                       log("============================================");
                       i++;
                   }
                   
                }    
       else if ("new".equals(action))
        {
            
            
            
            log("adding row...");
                               
                               
                               Integer lidvalue=null;
                               int xModelRow =-1;
                               int xSelectedRow = table.getSelectedRow();
                               if (xSelectedRow==-1)
                               {
                                log("row not selected");
                                //set creation inx at 0
                                xSelectedRow=0;
                               }
                               else
                               {
                                log("view row: " + xSelectedRow);
                               // x = table.convertRowIndexToModel(x);
                                xModelRow = table.getRowSorter().convertRowIndexToModel(xSelectedRow);
                                log("model row: " + xModelRow);
                                Object o = table.getModel().getValueAt(xModelRow, 1);
                               
                                if (o != null && o instanceof Integer)
                                {
                                    lidvalue = (Integer)o+1;
                                }
                               }

                               
                               log("inserting row at model index:" + xModelRow);
                               TestRowField data = null;// set def data in the model new TestRowField();                              
                               ttm.insertRow(xModelRow, data);
                               

                               
                               log("done");
                               //select rows
                               //converting back to Row to select newly created row
                               int newRowViewInx=table.convertRowIndexToView(xModelRow);
  
//                              
//                               int from = xModelRow;
//                               int to = table.convertRowIndexToModel(newRowViewInx);
//                               log("from:" + from + ", to:" + to);
//                                this goes only for model we need view
//                               ttm.reorder(from, to) ;
                               table.setRowSelectionInterval(newRowViewInx, newRowViewInx);      
                              
        }
    }
 
// end of main class
    
    
// inner classes 

 //*****row type********
  
private class TestRowField {
    private ArrayList<TestColumnField> cols; 

    /**
     * @return the cols
     */
    public ArrayList getCols() {
        return cols;
    }

    /**
     * @param cols the cols to set
     */
    public void setCols(ArrayList cols) {
        this.cols = cols;
    }
  }
   
    
/********** column type ******************/
  private  class TestColumnField 
    {
    private String name;
    private Object value;
    private String type;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    }
/********** table model ******************/
private class TestTableModel extends AbstractTableModel implements Reorderable
{
 private ArrayList<TestRowField> theData = new ArrayList();  // the list containing the objects (=the rows)

    private ArrayList<TestColumnField>  columnData;

    public int getMaxId()
    {
        int result=-1;
        for (TestRowField tr: (ArrayList<TestRowField>)theData)
        {
            Integer idValue = (Integer)((TestColumnField)tr.getCols().get(0)).getValue();
            if ( idValue > result
                )
            {
                result = idValue;
            }
        }
        return result+1;
    }
    
    public TestTableModel(ArrayList<TestColumnField> pColumnData, ArrayList<TestRowField> pData) 
    {
          theData = pData;
          columnData=pColumnData;
    }


   public List getData()
   {
       return theData;
   }
    public int getRowCount() 
    {
     //   System.out.println("size:"+theData.size());
        return theData.size();
         
    }
    
    @Override
    public int getColumnCount() 
    {
         return columnData.size();
    }
    
    @Override
    public void setValueAt(Object aValue,
                              int rowIndex,
                              int columnIndex)
    {
        log("sva: row:"+rowIndex+", columnIndex="+columnIndex);
        if (theData==null)
        {
           log("sva:empty data!");
            return;
        }
         
         TestRowField tr = (TestRowField)theData.get(rowIndex);
         if (tr==null)
         {
            log("sva: row null");
            return;
         }
         TestColumnField tc= (TestColumnField)tr.getCols().get(columnIndex);
         if (tc==null)
         {
            log("sva: col null");
            return;
         }
         tc.setValue(aValue);
           //values.set(row, value);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    @Override    
    public Object getValueAt(int row, int column) 
    {
        String m="gva: ";
//        System.out.println(m.concat("row: ")+row+", col: "+column);
        if (theData==null)
        {
            log(m+"empty data!");
        }
        
        TestRowField rowData = (TestRowField) theData.get(row);
        if (rowData==null)
        {
            log(m+"empty row");
            return null;
        }
         ArrayList<TestColumnField> cols = rowData.getCols();
        
         if (cols==null)
         {
//             System.out.println(m+"empty cols");
             return null;
         }
                      
     
         TestColumnField colData = cols.get(column);
//         if (colData.getValue()!=null)
//         {
//            if (colData.getValue()instanceof String)
//               log(m+"value for " + (colData.getName())+ " = '"+(String)colData.getValue()+"'");
//            else if 
//                 (colData.getValue()instanceof Integer)
//               log(m+"value for " + (colData.getName())+ " = '" +(Integer)colData.getValue()+"'");
//            else
//            {
//                log(m+"something wrong type not found");
//            }
//         }
//         else
//         {
//             log("value is null");
//         }
         return colData.getValue();
    }
    /**
     * 
     * @param row
     * @param rowData 
     */
    public void insertRow(int row, TestRowField rowData)
    {
        final String mp="insertRow ";
        if (rowData==null)
        {
            TestRowField tr=new TestRowField();
            ArrayList<TestColumnField> tc = new ArrayList(); //columnData;
           
            TestColumnField tcf = new TestColumnField();
            //id
            tcf.setValue(getMaxId());
            tc.add(tcf);
            //name
            tcf = new TestColumnField();
            tcf.setValue("New row model inx="+row);
            tc.add(tcf);
            //value
            tcf = new TestColumnField();
            tc.add(tcf);
            //tc=columnData;
            //tc.get(2).setValue("row="+row);
            //set id
           // tc.get(0).setValue(getMaxId());
            tr.setCols(tc);
            rowData=tr;
            tc=null;
        }
        if (theData!=null)
        {  
            //theData.
            theData.add(row,rowData);
        }
        else
        {
            theData = new ArrayList();
            theData.add(row,rowData);
        }
        fireTableRowsInserted(row, row);
        //TODO do we need this?
        //table.revalidate();
        //table.repaint();
    }
    /**
     * 
     * @param row 
     */
    public void removeRow (int row)
    {
        for (int i=theData.size()-1;i<theData.size();i--)
        {
            if (row==i)
            {
                theData.remove(row);
                fireTableDataChanged();
                return;
            }
        }
         
    }
    /**
     * 
     * @param row 
     */
    public void removeRow(TestRowField row)
    {
        theData.remove(row);        
    }
    
    //set headers
    @Override
    public String getColumnName(int col) {
        TestColumnField tcf = columnData.get(col);
        return tcf.getName();
    }
    
    @Override 
      public boolean isCellEditable(int row, int col) 
      { 
          if (col==0)
              return false;
          else
          return true; 
      } 
      
     public Class getColumnClass(int col) 
     {
          TestColumnField tcf = columnData.get(col);
          if ("text".equals(tcf.getType()))
                  return String.class;
          
              if ("num".equals(tcf.getType()))
                  return Integer.class;
      
              else 
              {
                  log("defaul class text");
                  return String.class;
              }
    }
     
     @Override
      public void reorder(int from, int to) 
      {
    Object o = theData.remove(from);
    theData.add(to,(TestRowField)o);
    fireTableDataChanged();
  }
}

public interface Reorderable {
   public void reorder(int fromIndex, int toIndex);
}
  
}


 

