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
        public ArrayList<Event> defaultList;
        
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
		defaultList = new ArrayList<>();
		frmMain = new JFrame ("Calendar Application");
                frmMain.setSize(660, 750);
		pane = frmMain.getContentPane();
		pane.setLayout(null);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//New Code
            frmMain.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            frmMain.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent event) {

                    frmMain.dispose();
                }
            });

            // New Code


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
		// New COde
		modelCalendarTable = new DefaultTableModel()
		{
			public boolean isCellEditable(int rowIndex, int mColIndex)
			{
					return false;
			}
		};
		//New Code
                
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
            public JButton addButton;
            public JScrollPane scrollList;

            public DefaultListModel modelEventsListTable;
            public JList eventsListTable;

            public ViewEvents(int day){
            	// Initialize the Frames
                frmMain.setEnabled(false);
                String dateHeader = "";
                dateHeader = dateHeader.concat(monthLabel.getText()+ " " + day + ", " + yearToday);

                mainFrame = new JFrame();
                mainFrame.setSize(400, 480);
                mainFrame.setTitle(dateHeader);
                pane = mainFrame.getContentPane();
                pane.setLayout(null);
                // Custom Exit on Close
                mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                mainFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent event) {
                        frmMain.setEnabled(true);
                        mainFrame.dispose();
                    }
                });

                // Add Button Initialize, Trigger pop up on click
				addButton = new JButton("Add");
				addButton.addActionListener(new ActionListener() {
					// Add Event Popup Initialize window
					JFrame frmAdd;
					JPanel panelAdd;
					Container paneAdd;
					JButton continueAddButton, cancelAddButton, selectColorButton;
					JLabel lblEventName, lblSelectColor, lblColor, lblSelectStart, lblSelectEnd;
					JTextField tfEventName;
					JCheckBox chkWholeDay;
					JComboBox cmbSYear, cmbSMon, cmbSDay, cmbSHour, cmbSMin;
					JComboBox cmbEYear, cmbEMon, cmbEDay, cmbEHour, cmbEMin;

					@Override
					public void actionPerformed(ActionEvent e) {
						//Add Event Popup
						mainFrame.setEnabled(false);

						frmAdd = new JFrame("Add Event Details");
						frmAdd.setSize(300, 340);
						paneAdd = frmAdd.getContentPane();
						paneAdd.setLayout(null);

						// Custom Exit on CLose
						frmAdd.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						frmAdd.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosing(WindowEvent event) {
								mainFrame.setEnabled(true);
								frmAdd.dispose();
							}
						});

						// Event Name Field
						lblEventName = new JLabel ("Event Title:");
						tfEventName = new JTextField();

						// Event Color Chooser
						lblSelectColor = new JLabel ("Event Color:");
						lblColor = new JLabel();
						lblColor.setOpaque(true);
						lblColor.setBackground(Color.black);
						selectColorButton = new JButton ("Choose");
						// JColorChooser UI to choose
						selectColorButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								Color c =JColorChooser.showDialog(null, "Choose a Color", lblColor.getBackground());
								if (c != null)
									lblColor.setBackground(c);
							}
						});

						// COntinue pushing through with adding
						continueAddButton = new JButton("Continue");
						continueAddButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								String status = tfEventName.getText();
								if (status != null && status != "") {
									Event newEvent = new Event();
									newEvent.setName(tfEventName.getText());
									newEvent.setStartDay((int)cmbSDay.getSelectedItem());
									newEvent.setStartMonth((int)cmbSMon.getSelectedItem());
									newEvent.setStartYear((int)cmbSYear.getSelectedItem());
									newEvent.setStartHour((int)cmbSHour.getSelectedItem());
									newEvent.setStartMinute((int)cmbSMin.getSelectedItem());

									newEvent.setEndDay((int)cmbEDay.getSelectedItem());
									newEvent.setEndMonth((int)cmbEMon.getSelectedItem());
									newEvent.setEndYear((int)cmbEYear.getSelectedItem());
									newEvent.setEndHour((int)cmbEHour.getSelectedItem());
									newEvent.setEndMinute((int)cmbEMin.getSelectedItem());
									newEvent.setColor(lblColor.getBackground());
									eventList.add(newEvent);
								}

								mainFrame.setEnabled(true);
								refreshViewEvents(day);
								frmAdd.dispose();
							}
						});

						//Cancel Pushing through with adding
						cancelAddButton = new JButton("Cancel");
						cancelAddButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								mainFrame.setEnabled(true);
								refreshViewEvents(day);
								frmAdd.dispose();
							}
						});

						// Event Time Choosing
						chkWholeDay = new JCheckBox("Whole Day", false);
						lblSelectStart = new JLabel("Choose Start Date and Time:");
						lblSelectEnd = new JLabel("Choose End Date and Time:");
						cmbSYear = new JComboBox();
						cmbSMon = new JComboBox();
						cmbSDay = new JComboBox();
						cmbSHour = new JComboBox();
						cmbSMin = new JComboBox();
						cmbEYear = new JComboBox();
						cmbEMon = new JComboBox();
						cmbEDay = new JComboBox();
						cmbEHour = new JComboBox();
						cmbEMin = new JComboBox();

						// Setting Contents of the Combo Boxes
						for (int i = yearBound-100; i <= yearBound+100; i++)
						{
							cmbSYear.addItem(i);
							cmbEYear.addItem(i);
						}

						for (int i = 0; i < 12; i++){
							cmbSMon.addItem(i+1);
							cmbEMon.addItem(i+1);
						}

						for (int i = 0;i < 24; i++){
							cmbSHour.addItem(i);
							cmbEHour.addItem(i);
						}

						for (int i = 0; i < 60; i++){
							cmbSMin.addItem(i);
							cmbEMin.addItem(i);
						}

						// Set Initially Selected Start Dates
						cmbSYear.setSelectedItem(yearToday);
						cmbSMon.setSelectedItem(monthToday + 1);

						// Initilize COntents of Start Day Combo Box
						int initSYear, initSMon;
						initSYear = (int)cmbSYear.getSelectedItem();
						initSMon = (int)cmbSMon.getSelectedItem() - 1;
						GregorianCalendar startDays = new GregorianCalendar(initSYear, initSMon, 1);
						int SMaxDays = startDays.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
						for (int i = 1; i <= SMaxDays; i++)
							cmbSDay.addItem(i);

						cmbSDay.setSelectedItem(day);
						cmbSHour.setSelectedItem(0);
						cmbSMin.setSelectedItem(0);

						//Item Listener to Change cmbEDays if year or month changes, for better UX
						cmbSYear.addItemListener(new ItemListener() {
							@Override
							public void itemStateChanged(ItemEvent e) {
								cmbSDay.removeAllItems();
								int initSYear, initSMon;
								initSYear = (int)cmbSYear.getSelectedItem();
								initSMon = (int)cmbSMon.getSelectedItem() - 1;
								GregorianCalendar startDays = new GregorianCalendar(initSYear, initSMon, 1);
								int SMaxDays = startDays.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
								for (int i = 1; i <= SMaxDays; i++)
									cmbSDay.addItem(i);
							}
						});

						cmbSMon.addItemListener(new ItemListener() {
							@Override
							public void itemStateChanged(ItemEvent e) {
								cmbSDay.removeAllItems();
								int initSYear, initSMon;
								initSYear = (int)cmbSYear.getSelectedItem();
								initSMon = (int)cmbSMon.getSelectedItem() - 1;
								GregorianCalendar startDays = new GregorianCalendar(initSYear, initSMon, 1);
								int SMaxDays = startDays.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
								for (int i = 1; i <= SMaxDays; i++)
									cmbSDay.addItem(i);
							}
						});

						// Set Initially End Dates
						cmbEYear.setSelectedItem(yearToday);
						cmbEMon.setSelectedItem(monthToday + 1);

						// Initilize COntents of End Day Combo Box
						int initEYear, initEMon;
						initEYear = (int)cmbEYear.getSelectedItem();
						initEMon = (int)cmbEMon.getSelectedItem() - 1;
						GregorianCalendar endDays = new GregorianCalendar(initEYear, initEMon, 1);
						int EMaxDays = endDays.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
						for (int i = 1; i <= EMaxDays; i++)
							cmbEDay.addItem(i);

						cmbEDay.setSelectedItem(day);
						cmbEHour.setSelectedItem(23);
						cmbEMin.setSelectedItem(59);

						//Item Listener to Change cmbEDays if year or month changes, for better UX
						cmbEYear.addItemListener(new ItemListener() {
							@Override
							public void itemStateChanged(ItemEvent e) {
								cmbEDay.removeAllItems();
								int initEYear, initEMon;
								initEYear = (int)cmbEYear.getSelectedItem();
								initEMon = (int)cmbEMon.getSelectedItem() - 1;
								GregorianCalendar endDays = new GregorianCalendar(initEYear, initEMon, 1);
								int EMaxDays = endDays.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
								for (int i = 1; i <= EMaxDays; i++)
									cmbEDay.addItem(i);
							}
						});

						cmbEMon.addItemListener(new ItemListener() {
							@Override
							public void itemStateChanged(ItemEvent e) {
								cmbEDay.removeAllItems();
								int initEYear, initEMon;
								initEYear = (int)cmbEYear.getSelectedItem();
								initEMon = (int)cmbEMon.getSelectedItem() - 1;
								GregorianCalendar endDays = new GregorianCalendar(initEYear, initEMon, 1);
								int EMaxDays = endDays.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
								for (int i = 1; i <= EMaxDays; i++)
									cmbEDay.addItem(i);
							}
						});

						// Checkbox ActionListener
						chkWholeDay.addChangeListener(new ChangeListener() {
							@Override
							public void stateChanged(ChangeEvent e) {
								if (chkWholeDay.isSelected()){
									cmbSHour.setSelectedItem(0);
									cmbSMin.setSelectedItem(0);
									cmbEHour.setSelectedItem(23);
									cmbEMin.setSelectedItem(59);

									cmbSHour.setEnabled(false);
									cmbSMin.setEnabled(false);
									cmbEHour.setEnabled(false);
									cmbEMin.setEnabled(false);
								} else {

									cmbSHour.setEnabled(true);
									cmbSMin.setEnabled(true);
									cmbEHour.setEnabled(true);
									cmbEMin.setEnabled(true);
								}
							}
						});

						// Main Panel
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
						panelAdd.add(chkWholeDay);
						panelAdd.add(lblSelectStart);
						panelAdd.add(lblSelectEnd);
						panelAdd.add(cmbSYear);
						panelAdd.add(cmbSMon);
						panelAdd.add(cmbSDay);
						panelAdd.add(cmbSHour);
						panelAdd.add(cmbSMin);
						panelAdd.add(cmbEYear);
						panelAdd.add(cmbEMon);
						panelAdd.add(cmbEDay);
						panelAdd.add(cmbEHour);
						panelAdd.add(cmbEMin);

						panelAdd.setBounds(5, 5, 275, 290);
						continueAddButton.setBounds(58, 250, 75, 25);
						cancelAddButton.setBounds(140, 250, 75, 25);
						lblEventName.setBounds(12, 25, 75, 25);
						tfEventName.setBounds(12, 50, 250, 25);
						lblSelectColor.setBounds(12, 80, 75, 25);
						lblColor.setBounds(80, 80, 25, 25);
						selectColorButton.setBounds(120, 80, 75, 25);
						chkWholeDay.setBounds(12, 115, 85, 25);
						lblSelectStart.setBounds(12, 140, 140, 25);
						cmbSYear.setBounds(13, 165, 50, 25);
						cmbSMon.setBounds(67, 165, 45, 25);
						cmbSDay.setBounds(116, 165, 45, 25);
						cmbSHour.setBounds(165, 165, 45, 25);
						cmbSMin.setBounds(214, 165, 45, 25);
						lblSelectEnd.setBounds(12, 190, 140, 25);
						cmbEYear.setBounds(13, 215, 50, 25);
						cmbEMon.setBounds(67, 215, 45, 25);
						cmbEDay.setBounds(116, 215, 45, 25);
						cmbEHour.setBounds(165, 215, 45, 25);
						cmbEMin.setBounds(214, 215, 45, 25);



						frmAdd.setLocationRelativeTo(mainFrame);
						frmAdd.setVisible(true);
						frmAdd.setResizable(false);
						refreshViewEvents(day);
					}
				});


				// Main Showing of all Events
                modelEventsListTable = new DefaultListModel();
                eventsListTable = new JList (modelEventsListTable);
                scrollList = new JScrollPane(eventsListTable);


                eventsListTable.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent mouseEvent) {
						if (eventsListTable.getModel().getSize() != 0) {
							Event eventSelected = (Event) eventsListTable.getSelectedValue();

							JFrame frmSelect;
							JPanel pnlSelect;
							Container pnSelect;
							JLabel lblStartDate, lblEndDate;
							JButton deleteButton, cancelButton;

							mainFrame.setEnabled(false);

							frmSelect = new JFrame("Event Info");
							frmSelect.setSize(350, 150);
							pnSelect = frmSelect.getContentPane();
							pnSelect.setLayout(null);

							// Custom Exit on CLose
							frmSelect.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
							frmSelect.addWindowListener(new WindowAdapter() {
								@Override
								public void windowClosing(WindowEvent event) {
									mainFrame.setEnabled(true);
									frmSelect.dispose();
								}
							});

							String startDateString = "";
							startDateString = startDateString.concat("From: " + eventSelected.getStartYear() + "/" + eventSelected.getString(eventSelected.getStartMonth() + 1) + "/" + eventSelected.getString(eventSelected.getStartDay()) + " " + eventSelected.getString(eventSelected.getStartHour()) + ":" + eventSelected.getString(eventSelected.getStartMinute()));
							String endDateString = "";
							endDateString = endDateString.concat("To:     " + eventSelected.getEndYear() + "/" + eventSelected.getString(eventSelected.getEndMonth() + 1) + "/" + eventSelected.getString(eventSelected.getEndDay()) + " " + eventSelected.getString(eventSelected.getEndHour()) + ":" + eventSelected.getString(eventSelected.getEndMinute()));

							lblStartDate = new JLabel(startDateString);
							lblEndDate = new JLabel(endDateString);

							deleteButton = new JButton("Delete");
							deleteButton.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this event?", "Delete Event", JOptionPane.YES_NO_OPTION);
									if (input == 0) {
										mainFrame.setEnabled(true);
										eventList.remove(eventSelected);
										refreshViewEvents(day);
										frmSelect.dispose();
									}
								}
							});

							cancelButton = new JButton("Cancel");
							cancelButton.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									mainFrame.setEnabled(true);
									refreshViewEvents(day);
									frmSelect.dispose();
								}
							});

							pnlSelect = new JPanel(null);
							TitledBorder eventTitle = BorderFactory.createTitledBorder(eventSelected.getName());
							eventTitle.setTitleJustification(TitledBorder.CENTER);
							pnlSelect.setBorder(eventTitle);

							pnSelect.add(pnlSelect);
							pnlSelect.add(lblStartDate);
							pnlSelect.add(lblEndDate);
							pnlSelect.add(deleteButton);
							pnlSelect.add(cancelButton);

							pnlSelect.setBounds(5, 5, 325, 102);
							lblStartDate.setBounds(100, 17, 130, 25);
							lblEndDate.setBounds(100, 32, 130, 25);
							deleteButton.setBounds(90, 65, 75, 25);
							cancelButton.setBounds(165, 65, 75, 25);

							frmSelect.setLocationRelativeTo(mainFrame);
							frmSelect.setVisible(true);
							frmSelect.setResizable(false);

						}
					}
				});

                mainPanel = new JPanel(null);
                TitledBorder title = BorderFactory.createTitledBorder("Events on This Day");
                title.setTitleJustification(TitledBorder.CENTER);
                mainPanel.setBorder(title);

                pane.add(mainPanel);
                mainPanel.add(addButton);
                mainPanel.add(scrollList);


                mainPanel.setBounds(10, 10, 365, 425);
                addButton.setBounds(153, 385, 55,25);
				scrollList.setBounds(32, 35, 300,340);


                mainFrame.setLocationRelativeTo(frmMain);
                mainFrame.setVisible(true);
                mainFrame.setResizable(false);


				eventsListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				refreshViewEvents(day);
            }

            // Refreshing the list of events upon adding or not
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
