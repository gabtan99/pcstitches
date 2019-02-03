/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge1;

/**
 *
 *
 * @author Arturo III
 */

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CalendarProgram{
	
        /**** Day Components ****/
	public int yearBound, monthBound, dayBound, yearToday, monthToday;

        /**** Swing Components ****/
        public JLabel monthLabel, yearLabel;
	public JButton btnPrev, btnNext;
        public JComboBox cmbYear;
	public JFrame frmMain;
	public Container pane;
	public JScrollPane scrollCalendarTable;
	public JPanel calendarPanel;
        
        /**** Calendar Table Components ***/
	public JTable calendarTable;
        public DefaultTableModel modelCalendarTable;

        /**** Events stored in Calendar *****/
        public ArrayList<Event> eventList;
        
        public void refreshCalendar(int month, int year)
        {
		String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int nod, som, i, j;
			
		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);
		if (month == 0 && year <= yearBound-10)
                    btnPrev.setEnabled(false);
		if (month == 11 && year >= yearBound+100)
                    btnNext.setEnabled(false);
                
		monthLabel.setText(months[month]);
		monthLabel.setBounds(320-monthLabel.getPreferredSize().width/2, 50, 360, 50);
                
		cmbYear.setSelectedItem(""+year);
		
		for (i = 0; i < 6; i++)
			for (j = 0; j < 7; j++)
				modelCalendarTable.setValueAt(null, i, j);
		
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);
		
		for (i = 1; i <= nod; i++)
                {
			int row = new Integer((i+som-2)/7);
			int column  =  (i+som-2)%7;
			modelCalendarTable.setValueAt(i, row, column);
		}

		calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new TableRenderer());
	}
        
	public CalendarProgram()
        {
		try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
		catch (Exception e) {}

		eventList = new ArrayList<>();
		frmMain = new JFrame ("Calendar Application");
                frmMain.setSize(660, 750);
		pane = frmMain.getContentPane();
		pane.setLayout(null);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		monthLabel = new JLabel ("January");
		yearLabel = new JLabel ("Change year:");
		cmbYear = new JComboBox();
		btnPrev = new JButton ("<<");
		btnNext = new JButton (">>");
		modelCalendarTable = new DefaultTableModel()
                {
                    public boolean isCellEditable(int rowIndex, int mColIndex)
                    {
                        return true;
                    }
                };
                
		calendarTable = new JTable(modelCalendarTable);
                calendarTable.addMouseListener(new MouseAdapter()   
                {  
                    public void mouseClicked(MouseEvent evt)  
                    {  
                        int col = calendarTable.getSelectedColumn();  
                        int row = calendarTable.getSelectedRow();

                        // NEW CODE--------------------------------------------------------------------
                        //Get the day
						GregorianCalendar cal = new GregorianCalendar(yearToday, monthToday, 1);
						int nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
						int som = cal.get(GregorianCalendar.DAY_OF_WEEK);
						int day;
						boolean found = false;
						for (day = 1; day <= nod && !found; day++)
						{
							int calcRow = new Integer((day+som-2)/7);
							int calcColumn = (day+som-2)%7;

							if (calcRow == row && calcColumn == col)
								found = true;
						}
                        day--;
						ViewEvents popup = new ViewEvents(day);
						// NEW CODE--------------------------------------------------------------------


                    }
                });
                
		scrollCalendarTable = new JScrollPane(calendarTable);
		calendarPanel = new JPanel(null);

		calendarPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));
		
		btnPrev.addActionListener(new btnPrev_Action());
		btnNext.addActionListener(new btnNext_Action());
		cmbYear.addActionListener(new cmbYear_Action());
		
		pane.add(calendarPanel);
		calendarPanel.add(monthLabel);
		calendarPanel.add(yearLabel);
		calendarPanel.add(cmbYear);
		calendarPanel.add(btnPrev);
		calendarPanel.add(btnNext);
		calendarPanel.add(scrollCalendarTable);
		
                calendarPanel.setBounds(0, 0, 640, 670);
                monthLabel.setBounds(320-monthLabel.getPreferredSize().width/2, 50, 200, 50);
		yearLabel.setBounds(20, 610, 160, 40);
		cmbYear.setBounds(460, 610, 160, 40);
		btnPrev.setBounds(20, 50, 100, 50);
		btnNext.setBounds(520, 50, 100, 50);
		scrollCalendarTable.setBounds(20, 100, 600, 500);
                
		frmMain.setResizable(false);
		frmMain.setVisible(true);
		
		GregorianCalendar cal = new GregorianCalendar();
		dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
		monthBound = cal.get(GregorianCalendar.MONTH);
		yearBound = cal.get(GregorianCalendar.YEAR);
		monthToday = monthBound; 
		yearToday = yearBound;
		
		String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
		for (int i=0; i<7; i++){
			modelCalendarTable.addColumn(headers[i]);
		}
		
		calendarTable.getParent().setBackground(calendarTable.getBackground()); //Set background

		calendarTable.getTableHeader().setResizingAllowed(false);
		calendarTable.getTableHeader().setReorderingAllowed(false);

		calendarTable.setColumnSelectionAllowed(true);
		calendarTable.setRowSelectionAllowed(true);
		calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		calendarTable.setRowHeight(76);
		modelCalendarTable.setColumnCount(7);
		modelCalendarTable.setRowCount(6);
		
		for (int i = yearBound-100; i <= yearBound+100; i++)
                {
			cmbYear.addItem(String.valueOf(i));
		}
		
		refreshCalendar (monthBound, yearBound); //Refresh calendar
	}

	class btnPrev_Action implements ActionListener
        {
		public void actionPerformed (ActionEvent e)
                {
			if (monthToday == 0)
                        {
				monthToday = 11;
				yearToday -= 1;
			}
			else
                        {
				monthToday -= 1;
			}
			refreshCalendar(monthToday, yearToday);
		}
	}
	class btnNext_Action implements ActionListener
        {
		public void actionPerformed (ActionEvent e)
                {
			if (monthToday == 11)
                        {
				monthToday = 0;
				yearToday += 1;
			}
			else
                        {
				monthToday += 1;
			}
			refreshCalendar(monthToday, yearToday);
		}
	}
	class cmbYear_Action implements ActionListener
        {
		public void actionPerformed (ActionEvent e)
                {
			if (cmbYear.getSelectedItem() != null)
                        {
				String b = cmbYear.getSelectedItem().toString();
				yearToday = Integer.parseInt(b);
				refreshCalendar(monthToday, yearToday);
			}
		}
	}

    class ViewEvents{
            public JFrame mainFrame;
            public JPanel mainPanel;
            public Container pane;
            public JButton addButton, deleteButton;
            public JScrollPane scrollList;

            public DefaultListModel modelEventsListTable;
            public JList eventsListTable;

            public ViewEvents(int day){
                frmMain.setEnabled(false);
                String dateHeader = "";
                dateHeader = dateHeader.concat(monthLabel.getText()+ " " + day + ", " + yearToday);

                mainFrame = new JFrame();
                mainFrame.setSize(500, 550);
                mainFrame.setTitle(dateHeader);
                pane = mainFrame.getContentPane();
                pane.setLayout(null);
                mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                mainFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent event) {
                        frmMain.setEnabled(true);
                        mainFrame.dispose();
                    }
                });

				addButton = new JButton("Add");
				addButton.addActionListener(new ActionListener() {
					JFrame frmAdd;
					JPanel panelAdd;
					Container paneAdd;
					JButton continueAddButton, cancelAddButton, selectColorButton;
					JLabel lblEventName, lblSelectColor, lblColor;
					JTextField tfEventName;
					@Override
					public void actionPerformed(ActionEvent e) {
						mainFrame.setEnabled(false);

						frmAdd = new JFrame("Add Event Details");
						frmAdd.setSize(300, 340);
						paneAdd = frmAdd.getContentPane();
						paneAdd.setLayout(null);
						frmAdd.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						frmAdd.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosing(WindowEvent event) {
								mainFrame.setEnabled(true);
								frmAdd.dispose();
							}
						});

						lblEventName = new JLabel ("Event Title:");
						tfEventName = new JTextField();

						lblSelectColor = new JLabel ("Event Color:");
						lblColor = new JLabel();
						lblColor.setOpaque(true);
						lblColor.setBackground(Color.black);
						selectColorButton = new JButton ("Choose");
						selectColorButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								Color c =JColorChooser.showDialog(null, "Choose a Color", lblColor.getBackground());
								if (c != null)
									lblColor.setBackground(c);
							}
						});

						continueAddButton = new JButton("Continue");
						continueAddButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								String status = tfEventName.getText();
								if (status != null && status != "") {
									Event newEvent = new Event();
									newEvent.setName(tfEventName.getText());
									newEvent.setStartDay(day);
									newEvent.setStartMonth(monthToday + 1);
									newEvent.setStartYear(yearToday);
									newEvent.setEndDay(day);
									newEvent.setEndMonth(monthToday + 1);
									newEvent.setEndYear(yearToday);
									newEvent.setColor(lblColor.getBackground());
									eventList.add(newEvent);
								}

								mainFrame.setEnabled(true);
								refreshViewEvents(day);
								frmAdd.dispose();
							}
						});
						cancelAddButton = new JButton("Cancel");
						cancelAddButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								mainFrame.setEnabled(true);
								refreshViewEvents(day);
								frmAdd.dispose();
							}
						});


						panelAdd = new JPanel(null);
						TitledBorder addTitle = BorderFactory.createTitledBorder("Fill in Event Details in the Fields");
						addTitle.setTitleJustification(TitledBorder.CENTER);
						panelAdd.setBorder(addTitle);

						paneAdd.add(panelAdd);
						panelAdd.add(continueAddButton);
						panelAdd.add(cancelAddButton);
						panelAdd.add(lblEventName);
						panelAdd.add(tfEventName);
						panelAdd.add(lblSelectColor);
						panelAdd.add(lblColor);
						panelAdd.add(selectColorButton);

						panelAdd.setBounds(5, 5, 275, 290);
						continueAddButton.setBounds(58, 250, 75, 25);
						cancelAddButton.setBounds(140, 250, 75, 25);
						lblEventName.setBounds(12, 30, 75, 25);
						tfEventName.setBounds(12, 55, 250, 25);
						lblSelectColor.setBounds(12, 100, 75, 25);
						lblColor.setBounds(80, 100, 25, 25);
						selectColorButton.setBounds(120, 100, 75, 25);

						frmAdd.setLocationRelativeTo(mainFrame);
						frmAdd.setVisible(true);
						frmAdd.setResizable(false);
						refreshViewEvents(day);
					}
				});
				deleteButton = new JButton("Delete");



                modelEventsListTable = new DefaultListModel();
                eventsListTable = new JList (modelEventsListTable);
                scrollList = new JScrollPane(eventsListTable);


                mainPanel = new JPanel(null);
                TitledBorder title = BorderFactory.createTitledBorder("Events on This Day");
                title.setTitleJustification(TitledBorder.CENTER);
                mainPanel.setBorder(title);

                pane.add(mainPanel);
                mainPanel.add(addButton);
                mainPanel.add(scrollList);


                mainPanel.setBounds(10, 10, 465, 495);
                addButton.setBounds(203, 430, 55,25);
				scrollList.setBounds(82, 60, 300,340);


                mainFrame.setLocationRelativeTo(frmMain);
                mainFrame.setVisible(true);
                mainFrame.setResizable(false);


				eventsListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				refreshViewEvents(day);
            }

            public void refreshViewEvents(int day){
				ArrayList<Event> eventsToday = new ArrayList<>();

				for (int i = 0; i < eventList.size(); i++) {
					Event e = eventList.get(i);
					if (e.getStartDay() == day && e.getStartYear() == yearToday && e.getStartMonth() == monthToday)
						eventsToday.add(e);
				}

				modelEventsListTable.removeAllElements();

				for (int i = 0; i < eventsToday.size(); i++){
					modelEventsListTable.addElement(eventsToday.get(i));
				}

				eventsListTable.setCellRenderer(new ListRenderer());
			}
    }
}
