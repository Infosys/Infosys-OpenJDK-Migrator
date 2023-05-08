/*
* Copyright 2018 Infosys Ltd.
* Version: 1.0.0
*Use of this source code is governed by MIT license that can be found in the LICENSE file or at https://opensource.org/licenses/MIT.
*/

/*
* Date: 06-May-2019
* @version 1.0.0
* Description: 
*/

package com.infy.openjdk.report;

import static net.sf.dynamicreports.report.builder.DynamicReports.cht;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.exp;
import static net.sf.dynamicreports.report.builder.DynamicReports.grp;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.infy.openjdk.ui.View;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.chart.Bar3DChartBuilder;
import net.sf.dynamicreports.report.builder.chart.BarChartBuilder;
import net.sf.dynamicreports.report.builder.chart.PieChartBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.datasource.DRDataSource;

/*********************************************************************************
 * 1.Jasper Report for Completed Migration and Assisted Remediation
 * 
 *
 * 
 ***********************************************************************************/

public class JasperMigrationReport {

	/**
	 * @param list
	 * @param headerList
	 * @return
	 */
	public JasperReportBuilder createReport12(List<Map<String, String>> list,List<String> headerList) {
		JasperReportBuilder report = report();
		if (!list.isEmpty()) {
			View.logger.info("size" + list.size());
			List<Map<String, String>> viewer2DataList = list;
			View.logger.info("header" + headerList.size());
			TextColumnBuilder<String> objname = col.column(headerList.get(0), "objectname", type.stringType())
					.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<String> objtype = col.column(headerList.get(1), "objecttype", type.stringType())
					.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<String> objstatus = col.column(headerList.get(2), "objectstatus", type.stringType())
					.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<String> col5 = col.column(headerList.get(3), "column5", type.stringType())
					.setHorizontalAlignment(HorizontalAlignment.LEFT);

			report.setPageFormat(PageType.B4);
			StyleBuilder titleStyle1 = stl.style(stl.style(3).bold()).setFontSize(10)
					.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBackgroundColor(Color.LIGHT_GRAY)
					.setBorder(stl.pen1Point()).setLeftIndent(10);
			StyleBuilder titleStyle = stl.style(stl.style(3).bold()).setFontSize(10)
					.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setForegroundColor(new Color(153, 0, 0))
					.setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
			StyleBuilder titleStyle4 = stl.style(stl.style(3).bold()).setFontSize(10)
					.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setForegroundColor(Color.BLACK)
					.setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
			report.columns(objname.setStyle(titleStyle4), objtype.setStyle(titleStyle4),  objstatus.setStyle(titleStyle),col5.setStyle(titleStyle4)).setColumnTitleStyle(titleStyle1);

			DRDataSource dataSource = new DRDataSource("objectname", "objecttype", "objectstatus","column5");
			View.logger.info(headerList.size()+"");
			for (int i = 0; i < viewer2DataList.size(); i++) {				
				dataSource.add(viewer2DataList.get(i).get(headerList.get(0)),
						viewer2DataList.get(i).get(headerList.get(1)),
						viewer2DataList.get(i).get(headerList.get(2)),viewer2DataList.get(i).get(headerList.get(3)));
			}			 
			StyleBuilder titleStyle2 = stl.style(3).setFontSize(10)
					.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setLeftPadding(10)
					.setBackgroundColor(Color.WHITE).setBorder(stl.pen1Point().setLineColor(Color.LIGHT_GRAY));
			report.setDataSource(dataSource).setColumnStyle(titleStyle2);

		}
		return report;
	} 
	
	/**
	 * @param viewer2DataList
	 * @param headerList
	 * @return
	 */
	public JasperReportBuilder createReport10(List<Map<String, String>> viewer2DataList,List<String> headerList) {
		JasperReportBuilder report = report();			
		View.logger.info("header" + headerList.size());
		TextColumnBuilder<String> objname = col.column(headerList.get(0), "objectname", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> objtype = col.column(headerList.get(1), "objecttype", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> objcount = col.column(headerList.get(2), "objectcount", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> objstatus = col.column(headerList.get(3), "objectstatus", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		report.setPageFormat(PageType.B4);
		StyleBuilder titleStyle1 = stl.style(stl.style(3).bold()).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBackgroundColor(Color.LIGHT_GRAY)
			.setBorder(stl.pen1Point()).setLeftIndent(10);
		StyleBuilder titleStyle = stl.style(stl.style(3).bold()).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setForegroundColor(new Color(153, 0, 0))
			.setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
		StyleBuilder titleStyle3 = stl.style(stl.style(3)).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
		report.columns(objname.setStyle(titleStyle3), objtype.setFixedWidth(60).setStyle(titleStyle3), objcount.setStyle(titleStyle), objstatus.setStyle(titleStyle3)).setColumnTitleStyle(titleStyle1);

		DRDataSource dataSource = new DRDataSource("objectname", "objecttype", "objectcount", "objectstatus");
		View.logger.info(headerList.size()+"");
		if (!viewer2DataList.isEmpty()) {
			for (int i = 0; i < viewer2DataList.size(); i++) {
				dataSource.add(viewer2DataList.get(i).get(headerList.get(0)),
						viewer2DataList.get(i).get(headerList.get(1)), 
						viewer2DataList.get(i).get(headerList.get(2)).trim(),
						viewer2DataList.get(i).get(headerList.get(3)).trim());
			}
		}
		else {
				dataSource.add("-","-","-","-");
		}

		StyleBuilder titleStyle2 = stl.style(3).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setLeftPadding(10)
			.setBackgroundColor(Color.WHITE).setBorder(stl.pen1Point().setLineColor(Color.LIGHT_GRAY));
		report.setDataSource(dataSource).setColumnStyle(titleStyle2);	
		return report;

	}

	/**
	 * @param viewer2DataList
	 * @param headerList
	 * @return
	 */
	public JasperReportBuilder createReport11(List<Map<String, String>> viewer2DataList,List<String> headerList) {
		JasperReportBuilder report = report();	
		View.logger.info("size" + viewer2DataList.size());	
		String fileName = headerList.get(0);
		String lineNo = headerList.get(1);
		String incompatibility = headerList.get(2);
		String possibleReplacement = headerList.get(3);
		String references = headerList.get(4);
		String strcomplexity = headerList.get(5);
		String strPackage = headerList.get(6);
		View.logger.info("header" + headerList.size());
		TextColumnBuilder<String> objname = col.column(fileName, "objectname", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> objtype = col.column(lineNo, "objecttype", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> objcount = col.column(incompatibility, "objectcount", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);			
		TextColumnBuilder<String> objstatus = col.column(possibleReplacement, "objectstatus", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> moreDetails = col.column(references, "moreDetails", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> complexity = col.column(strcomplexity, "complexity", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> packages = col.column(strPackage, "packages", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		report.setPageFormat(PageType.B4);
		StyleBuilder titleStyle1 = stl.style(stl.style(3).bold()).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBackgroundColor(Color.LIGHT_GRAY)
			.setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
		StyleBuilder titleStyle = stl.style(stl.style(3).bold()).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setForegroundColor(new Color(153, 0, 0))
			.setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
		StyleBuilder titleStyle3 = stl.style(stl.style(3)).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);			
		StyleBuilder titleStyle4 = stl.style(stl.style(3)).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setForegroundColor(Color.black)
			.setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10).setItalic(true);
		report.columns(packages.setFixedWidth(70).setStyle(titleStyle3),objname.setStyle(titleStyle3), objtype.setFixedWidth(55).setStyle(titleStyle3), objcount.setStyle(titleStyle), objstatus.setStyle(titleStyle3), moreDetails.setStyle(titleStyle4),complexity.setFixedWidth(70).setStyle(titleStyle3)).setColumnTitleStyle(titleStyle1);

		DRDataSource dataSource = new DRDataSource("packages","objectname", "objecttype", "objectcount", "objectstatus", "moreDetails","complexity");
		View.logger.info(headerList.size()+"");
		if (!viewer2DataList.isEmpty()) {
			for (int i = 0; i < viewer2DataList.size(); i++) {				
				dataSource.add(viewer2DataList.get(i).get(strPackage),
						viewer2DataList.get(i).get(fileName),
						viewer2DataList.get(i).get(lineNo),
						viewer2DataList.get(i).get(incompatibility),
						viewer2DataList.get(i).get(possibleReplacement),
						viewer2DataList.get(i).get(references),
						viewer2DataList.get(i).get(strcomplexity));				
			}
		}
		else {
			dataSource.add("-","-","-","-","-","-","-");
			}

		StyleBuilder titleStyle2 = stl.style(3).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setLeftPadding(10)
			.setBackgroundColor(Color.WHITE).setBorder(stl.pen1Point().setLineColor(Color.LIGHT_GRAY));
		report.setDataSource(dataSource).setColumnStyle(titleStyle2);		
		return report;
	}

	/**
	 * @param viewer2DataList
	 * @param headerList
	 * @return
	 */
	public JasperReportBuilder createReport13(List<Map<String, String>> viewer2DataList,List<String> headerList) {
		JasperReportBuilder report = report();
		if (!viewer2DataList.isEmpty()) {
			View.logger.info("size" + viewer2DataList.size());
			View.logger.info("header" + headerList.size());
			TextColumnBuilder<String> objname = col.column(headerList.get(0), "objectname", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<String> objtype = col.column(headerList.get(1), "objecttype", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<String> objcount = col.column(headerList.get(2), "objectcount", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<String> objstatus = col.column(headerList.get(3), "objectstatus", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<String> col5 = col.column(headerList.get(4), "column5", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			report.setPageFormat(PageType.B4);
			StyleBuilder titleStyle1 = stl.style(stl.style(3).bold()).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBackgroundColor(Color.LIGHT_GRAY)
				.setBorder(stl.pen1Point()).setLeftIndent(10);
			StyleBuilder titleStyle = stl.style(stl.style(3).bold()).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setForegroundColor(new Color(153, 0, 0))
				.setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
			StyleBuilder titleStyle4 = stl.style(stl.style(3).bold()).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setForegroundColor(Color.GREEN)
				.setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
			StyleBuilder titleStyle3 = stl.style(stl.style(3)).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
			report.columns(objname.setStyle(titleStyle3), objtype.setFixedWidth(60).setStyle(titleStyle3), objcount.setStyle(titleStyle), objstatus.setStyle(titleStyle3),col5.setStyle(titleStyle4)).setColumnTitleStyle(titleStyle1);

			DRDataSource dataSource = new DRDataSource("objectname", "objecttype", "objectcount", "objectstatus","column5");
			View.logger.info(headerList.size()+"");
			for (int i = 0; i < viewer2DataList.size(); i++) {
				dataSource.add(viewer2DataList.get(i).get(headerList.get(0)),
						viewer2DataList.get(i).get(headerList.get(1)), viewer2DataList.get(i).get(headerList.get(2)).trim(),
						viewer2DataList.get(i).get(headerList.get(3)).trim(),viewer2DataList.get(i).get(headerList.get(4)));
			}

			StyleBuilder titleStyle2 = stl.style(3).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setLeftPadding(10)
				.setBackgroundColor(Color.WHITE).setBorder(stl.pen1Point().setLineColor(Color.LIGHT_GRAY));
			report.setDataSource(dataSource).setColumnStyle(titleStyle2);

		}
		return report;

	}

	/**
	 * @return report
	 */
	public JasperReportBuilder addCodeSnippet() {
		JasperReportBuilder report = report();
		DRDataSource dataSource = new DRDataSource("Snippets");
		TextColumnBuilder<String> objname = col.column("Snippets", type.stringType()).setAnchorName("Calender");
		URL url;
		try {
			url = new URL("platform:/plugin/OpenJDKMigrator/com/infy/openjdk/configuration/SourceCalendar.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()),8192);
			String line = null;
			while((line = br.readLine())!= null) {
				dataSource.add(line+"\n");
			}					
		} 
		catch (IOException e) {			
			View.logger.error(e.getMessage(),e);
		}
		report.columns(objname);
		report.setPageFormat(PageType.B4).setDataSource(dataSource);				
		return report;
	}

	/**
	 * @param list
	 * @param headerList
	 * @return
	 */
	public JasperReportBuilder createReport15(List<Map<String, String>> list,List<String> headerList) {
		JasperReportBuilder report = report();		
		View.logger.info("size" + list.size());
		List<Map<String, String>> viewer2DataList = list;
		View.logger.info("header" + headerList.size());
		TextColumnBuilder<String> objname = col.column(headerList.get(0), "objectname", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> objtype = col.column(headerList.get(1), "objecttype", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> objcount = col.column(headerList.get(2), "objectcount", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> objstatus = col.column(headerList.get(3), "objectstatus", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> col5 = col.column(headerList.get(4), "column5", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> col6 = col.column(headerList.get(5), "column6", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> col7 = col.column(headerList.get(6), "column7", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		report.setPageFormat(PageType.B4);
		StyleBuilder titleStyle1 = stl.style(stl.style(3).bold()).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBackgroundColor(Color.LIGHT_GRAY)
			.setBorder(stl.pen1Point()).setLeftIndent(10);

		StyleBuilder titleStyle4 = stl.style(stl.style(3)).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setForegroundColor(Color.BLACK)
			.setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
		report.columns(objname.setStyle(titleStyle4), objtype.setStyle(titleStyle4),  objcount.setStyle(titleStyle4),objstatus.setStyle(titleStyle4),col5.setStyle(titleStyle4),col6.setStyle(titleStyle4),col7.setStyle(titleStyle4)).setColumnTitleStyle(titleStyle1);

		DRDataSource dataSource = new DRDataSource("objectname", "objecttype","objectcount", "objectstatus","column5","column6","column7");
		View.logger.info(headerList.size()+"");
		if (!list.isEmpty()) {
			for (int i = 0; i < viewer2DataList.size(); i++) {				
				dataSource.add(viewer2DataList.get(i).get(headerList.get(0)),
					viewer2DataList.get(i).get(headerList.get(1)),
					viewer2DataList.get(i).get(headerList.get(2)),
					viewer2DataList.get(i).get(headerList.get(3)),
					viewer2DataList.get(i).get(headerList.get(4)),
					viewer2DataList.get(i).get(headerList.get(5)),
					viewer2DataList.get(i).get(headerList.get(6)));
			}
		}
		else {
			dataSource.add("-","-","-","-","-","-","-");
		}
			 
		StyleBuilder titleStyle2 = stl.style(3).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setLeftPadding(10)
			.setBackgroundColor(Color.WHITE).setBorder(stl.pen1Point().setLineColor(Color.LIGHT_GRAY));
		report.setDataSource(dataSource).setColumnStyle(titleStyle2);

		return report;
	}
		
	/**
	 * @param noOfApplications
	 * @param totalFilesScanned
	 * @param totalAutoMated
	 * @param totalAssisted
	 * @param colsList
	 * @return report
	 */
	public JasperReportBuilder createReportSummaryBatch(int noOfApplications,int totalFilesScanned,int totalAutoMated,int totalAssisted,
			List<String> colsList) {
		JasperReportBuilder report = report();			
		TextColumnBuilder<String> objname = col.column(colsList.get(0), "objectname", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> objtype = col.column(colsList.get(1), "objecttype", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> objcount = col.column(colsList.get(2), "objectcount", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> objstatus = col.column(colsList.get(3), "objectstatus", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> col5 = col.column(colsList.get(4), "column5", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		report.setPageFormat(PageType.B4);
		StyleBuilder titleStyle1 = stl.style(stl.style(3).bold()).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBackgroundColor(Color.LIGHT_GRAY)
			.setBorder(stl.pen1Point()).setLeftIndent(10);
		StyleBuilder titleStyle4 = stl.style(stl.style(3).bold()).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setForegroundColor(Color.BLACK)
			.setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
		report.columns(objname.setStyle(titleStyle4), objtype.setStyle(titleStyle4),  objcount.setStyle(titleStyle4),objstatus.setStyle(titleStyle4),col5.setStyle(titleStyle4)).setColumnTitleStyle(titleStyle1);

		DRDataSource dataSource = new DRDataSource("objectname", "objecttype","objectcount", "objectstatus","column5");
			View.logger.info(colsList.size()+"");					
			dataSource.add(String.valueOf(noOfApplications),
				String.valueOf(totalFilesScanned),
				String.valueOf(totalAutoMated+totalAssisted),
				String.valueOf(totalAutoMated),
				String.valueOf(totalAssisted));
				 
		StyleBuilder titleStyle2 = stl.style(3).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setLeftPadding(10)
			.setBackgroundColor(Color.WHITE).setBorder(stl.pen1Point().setLineColor(Color.LIGHT_GRAY));
		report.setDataSource(dataSource).setColumnStyle(titleStyle2);			
		return report;

	}	

	/**
	 * @param list
	 * @param colsList
	 * @return report
	 */
	public JasperReportBuilder createReportSummaryBreakDownBatch(List<List<Map<String, String>>> list,List<String> colsList) {
		JasperReportBuilder report = report();
		if (!list.isEmpty()) {
			View.logger.info("size" + list.size());				
			List<Map<String, String>> viewer2DataList = list.get(0);
			TextColumnBuilder<String> objname = col.column(colsList.get(0), "objectname", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<String> objtype = col.column(colsList.get(1), "objecttype", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<String> objcount = col.column(colsList.get(2), "objectcount", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<String> objstatus = col.column(colsList.get(3), "objectstatus", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<String> col5 = col.column(colsList.get(4), "column5", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<Double> col6 = col.column(colsList.get(5), "column6", type.doubleType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<Double> col7 = col.column(colsList.get(6), "column7", type.doubleType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			report.setPageFormat(PageType.B4);
			StyleBuilder titleStyle1 = stl.style(stl.style(3).bold()).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBackgroundColor(Color.LIGHT_GRAY)
				.setBorder(stl.pen1Point()).setLeftIndent(10);
			StyleBuilder titleStyle4 = stl.style(stl.style(3).bold()).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setForegroundColor(Color.BLACK)
				.setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
			report.columns(objname.setStyle(titleStyle4), objtype.setStyle(titleStyle4),  objcount.setStyle(titleStyle4),objstatus.setStyle(titleStyle4),col5.setStyle(titleStyle4),col6.setStyle(titleStyle4),col7.setStyle(titleStyle4)).setColumnTitleStyle(titleStyle1);

			DRDataSource dataSource = new DRDataSource("objectname", "objecttype","objectcount", "objectstatus","column5","column6","column7");
			View.logger.info(colsList.size()+"");
			for (int i = 0; i < viewer2DataList.size(); i++) {				
				dataSource.add(viewer2DataList.get(i).get(colsList.get(0)),
					viewer2DataList.get(i).get(colsList.get(1)),
					viewer2DataList.get(i).get(colsList.get(2)),
					viewer2DataList.get(i).get(colsList.get(3)),
					viewer2DataList.get(i).get(colsList.get(4)),
					Double.parseDouble(viewer2DataList.get(i).get(colsList.get(5))),
					Double.parseDouble(viewer2DataList.get(i).get(colsList.get(6))));
			}

			Bar3DChartBuilder itemChart = cht.bar3DChart().setTitle("Assisted Remediation Graph").setCategory(objname).addSerie(cht.serie(col6),cht.serie(col7));
			ColumnGroupBuilder itemGroup = grp.group(objname);
			itemGroup.setPrintSubtotalsWhenExpression(exp.printWhenGroupHasMoreThanOneRow(itemGroup));
			report.summary(cmp.verticalGap(10),cmp.horizontalList(itemChart));								 
			StyleBuilder titleStyle2 = stl.style(3).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setLeftPadding(10)
				.setBackgroundColor(Color.WHITE).setBorder(stl.pen1Point().setLineColor(Color.LIGHT_GRAY));
			report.setDataSource(dataSource).setColumnStyle(titleStyle2);
		}
		return report;

	}
		
		
	/**
	 * @param list
	 * @param colsList
	 * @return
	 */
	public JasperReportBuilder createReportMigrationSummaryBatch(List<List<Map<String, String>>> list,List<String> colsList) {
		JasperReportBuilder report = report();
		if (!list.isEmpty()) {
			View.logger.info("size" + list.size());				
			List<Map<String, String>> viewer2DataList = list.get(0);
			TextColumnBuilder<String> objname = col.column(colsList.get(0), "objectname", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<String> objtype = col.column(colsList.get(1), "objecttype", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			report.setPageFormat(PageType.B4);
			
			StyleBuilder titleStyle1 = stl.style(stl.style(3).bold()).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBackgroundColor(Color.LIGHT_GRAY)
				.setBorder(stl.pen1Point()).setLeftIndent(10);
			StyleBuilder titleStyle4 = stl.style(stl.style(3).bold()).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setForegroundColor(Color.BLACK)
				.setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
			report.columns(objname.setStyle(titleStyle4), objtype.setStyle(titleStyle4)).setColumnTitleStyle(titleStyle1);

			DRDataSource dataSource = new DRDataSource("objectname", "objecttype");
			View.logger.info(colsList.size()+"");
			for (int i = 0; i < viewer2DataList.size(); i++) {					
				dataSource.add(viewer2DataList.get(i).get(colsList.get(0)),
				viewer2DataList.get(i).get(colsList.get(1)));
			}
				 
			StyleBuilder titleStyle2 = stl.style(3).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setLeftPadding(10)
				.setBackgroundColor(Color.WHITE).setBorder(stl.pen1Point().setLineColor(Color.LIGHT_GRAY));
			report.setDataSource(dataSource).setColumnStyle(titleStyle2);
		}
		return report;
		
	}
		
	/**
	 * @param list
	 * @param colsList
	 * @return
	 */
	public JasperReportBuilder createReport17(List<Map<String, String>> list, List<String> colsList) {
		JasperReportBuilder report = report();
		if (!list.isEmpty()) {
			View.logger.info("size" + list.size());
			List<String> headerList = colsList;
			List<Map<String, String>> viewer2DataList = list;
			View.logger.info("header" + headerList.size());
			TextColumnBuilder<String> objname = col.column(headerList.get(0), "objectname", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<String> objtype = col.column(headerList.get(1), "objecttype", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<Double> col2 = col.column(headerList.get(2), "col2", type.doubleType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			report.setPageFormat(PageType.B4);
			StyleBuilder titleStyle1 = stl.style(stl.style(3).bold()).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBackgroundColor(Color.LIGHT_GRAY)
				.setBorder(stl.pen1Point()).setLeftIndent(10);
			StyleBuilder titleStyle4 = stl.style(stl.style(3)).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setForegroundColor(Color.BLACK)
				.setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
				report.columns(objname.setStyle(titleStyle4),objtype.setStyle(titleStyle4),col2.setStyle(titleStyle4)  ).setColumnTitleStyle(titleStyle1);

			DRDataSource dataSource = new DRDataSource("objectname", "objecttype","col2");
			View.logger.info(colsList.size()+"");
			for (int i = 0; i < viewer2DataList.size(); i++) {
				dataSource.add(viewer2DataList.get(i).get(headerList.get(0)),
				viewer2DataList.get(i).get(headerList.get(1)),
				Double.parseDouble(viewer2DataList.get(i).get(headerList.get(2))));
			}

			PieChartBuilder pieChart = cht.pieChart();
			pieChart.setShowValues(true);
			pieChart.setKey(objtype);
			pieChart.series(cht.serie(col2));								 
			StyleBuilder titleStyle2 = stl.style(3).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setLeftPadding(10)
				.setBackgroundColor(Color.WHITE).setBorder(stl.pen1Point().setLineColor(Color.LIGHT_GRAY));
			report.setDataSource(dataSource).setColumnStyle(titleStyle2);
			report.summary(pieChart);
		}
		return report;
	}
		
	/**
	 * @param list
	 * @param colsList
	 * @return
	 */
	public JasperReportBuilder createReportEffortBatch(List<List<Map<String, String>>> list,List<String> colsList) {
		JasperReportBuilder report = report();
		if (!list.isEmpty()) {
			View.logger.info("size" + list.size());
			List<Map<String, String>> viewer2DataList = list.get(0);
			View.logger.info("header" + colsList.size());
			TextColumnBuilder<String> objname = col.column(colsList.get(0), "objectname", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<String> simple = col.column(colsList.get(1), "simple", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);				
			TextColumnBuilder<String> medium = col.column(colsList.get(2), "medium", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<String> complex = col.column(colsList.get(3), "complex", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<Double> objtype = col.column(colsList.get(4), "objecttype", type.doubleType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<Double> effort = col.column(colsList.get(5), "effort", type.doubleType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			report.setPageFormat(PageType.B4);
			StyleBuilder titleStyle1 = stl.style(stl.style(3).bold()).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBackgroundColor(Color.LIGHT_GRAY)
				.setBorder(stl.pen1Point()).setLeftIndent(10);
			StyleBuilder titleStyle4 = stl.style(stl.style(3).bold()).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setForegroundColor(Color.BLACK)
				.setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
			report.columns(objname.setStyle(titleStyle4),simple.setStyle(titleStyle4),medium.setStyle(titleStyle4),complex.setStyle(titleStyle4),objtype.setStyle(titleStyle4),effort.setStyle(titleStyle4) ).setColumnTitleStyle(titleStyle1);

			DRDataSource dataSource = new DRDataSource("objectname","simple","medium","complex","objecttype","effort");
				View.logger.info(colsList.size()+"");
			for (int i = 0; i < viewer2DataList.size(); i++) {					
				dataSource.add(viewer2DataList.get(i).get(colsList.get(0)),
				viewer2DataList.get(i).get(colsList.get(1)),
				viewer2DataList.get(i).get(colsList.get(2)),
				viewer2DataList.get(i).get(colsList.get(3)),
				Double.valueOf(viewer2DataList.get(i).get(colsList.get(4))),
				Double.valueOf(viewer2DataList.get(i).get(colsList.get(5))));
			}
			Bar3DChartBuilder itemChart = cht.bar3DChart()
                .setTitle("Effort Break Down Graph")
                .setCategory(objname)
                .addSerie(cht.serie(objtype),cht.serie(effort));

			ColumnGroupBuilder itemGroup = grp.group(objname);
			itemGroup.setPrintSubtotalsWhenExpression(exp.printWhenGroupHasMoreThanOneRow(itemGroup));
			report.summary(cmp.verticalGap(10),cmp.verticalList(itemChart));				 
		}
		return report;
	}
						
	/**
	 * @param list
	 * @param headerList
	 * @return report
	 */
	public JasperReportBuilder createReport18(List<Map<String, String>> list,List<String> headerList) {
		JasperReportBuilder report = report();
		List<Map<String, String>> viewer2DataList = list;
		View.logger.info("header" + headerList.size());
		TextColumnBuilder<String> complexity = col.column(headerList.get(0), "objectname", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> instances = col.column(headerList.get(1), "objecttype", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> effort = col.column(headerList.get(2), "objectstatus", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> col4 = col.column(headerList.get(3), "col4", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.RIGHT);
		//TextColumnBuilder<String> col5 = col.column(headerList.get(4), "col5", type.stringType())
		report.setPageFormat(PageType.B4);
		StyleBuilder titleStyle1 = stl.style(stl.style(3).bold()).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBackgroundColor(Color.LIGHT_GRAY)
			.setBorder(stl.pen1Point()).setLeftIndent(10);
		StyleBuilder titleStyle4 = stl.style(stl.style(3)).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setForegroundColor(Color.BLACK)
			.setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
		report.columns(complexity.setStyle(titleStyle4), instances.setStyle(titleStyle4),  effort.setStyle(titleStyle4),col4.setStyle(titleStyle4)).setColumnTitleStyle(titleStyle1);
		
		DRDataSource dataSource = new DRDataSource("objectname", "objecttype", "objectstatus","col4");
			View.logger.info(headerList.size()+"");
			if (!list.isEmpty()) {
				for (int i = 0; i < viewer2DataList.size(); i++) {					
					dataSource.add(viewer2DataList.get(i).get(headerList.get(0)),
					viewer2DataList.get(i).get(headerList.get(1)),
					viewer2DataList.get(i).get(headerList.get(2)),
					viewer2DataList.get(i).get(headerList.get(3)));
				}
			}
			else {
				dataSource.add("-","-","-","-");
			}

		StyleBuilder titleStyle2 = stl.style(3).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setLeftPadding(10)
			.setBackgroundColor(Color.WHITE).setBorder(stl.pen1Point().setLineColor(Color.LIGHT_GRAY));
		report.setDataSource(dataSource).setColumnStyle(titleStyle2);			
		return report;
	}
		
	/**
	 * @param viewer2DataList
	 * @param headerList
	 * @return report
	 */
	public JasperReportBuilder createPieChart(List<Map<String, String>> viewer2DataList, List<String> headerList) {
		JasperReportBuilder report = report();
		View.logger.info("Inside piechart");
		if (!headerList.isEmpty()) {
			TextColumnBuilder<String> objectname = col.column(headerList.get(0), "objectname", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<Double> objecttype = col.column(headerList.get(1), "objecttype", type.doubleType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			report.setPageFormat(PageType.B4);
			StyleBuilder titleStyle1 = stl.style(stl.style(3).bold()).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBackgroundColor(Color.LIGHT_GRAY)
				.setBorder(stl.pen1Point()).setLeftIndent(10);
			StyleBuilder titleStyle4 = stl.style(stl.style(3)).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setForegroundColor(Color.BLACK)
				.setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
			report.columns(objectname.setStyle(titleStyle4), objecttype.setStyle(titleStyle4)).setColumnTitleStyle(titleStyle1);

			DRDataSource dataSource = new DRDataSource("objectname", "objecttype");
			View.logger.info(headerList.size()+"");
			for (int i = 0; i < viewer2DataList.size(); i++) {					
				dataSource.add(viewer2DataList.get(i).get(headerList.get(0)),
				Double.parseDouble(viewer2DataList.get(i).get(headerList.get(1))));
			}

			PieChartBuilder pieChart = cht.pieChart();
			pieChart.setShowValues(true);
			pieChart.setKey(objectname);
			pieChart.series(cht.serie(objecttype));
			report.setDataSource(dataSource);
			report.summary(pieChart);
		}
		return report;

	}		
		
		
				
	/**
	 * @param list
	 * @param headerList
	 * @return report
	 */
	public JasperReportBuilder createpackagereport(List<Map<String, String>> list, List<String> headerList) {
		JasperReportBuilder report = report();
		View.logger.info("header" + headerList.size());
		TextColumnBuilder<String> objname = col.column(headerList.get(0), "objectname", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<Integer> objtype = col.column(headerList.get(1), "objecttype", type.integerType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		report.setPageFormat(PageType.B4);
		StyleBuilder titleStyle1 = stl.style(stl.style(3).bold()).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBackgroundColor(Color.LIGHT_GRAY)
			.setBorder(stl.pen1Point()).setLeftIndent(10);
		StyleBuilder titleStyle3 = stl.style(stl.style(3)).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
		report.columns(objname.setStyle(titleStyle3), objtype.setStyle(titleStyle3)).setColumnTitleStyle(titleStyle1);

		DRDataSource dataSource = new DRDataSource("objectname", "objecttype");
		View.logger.info(headerList.size()+"");
		if (!list.isEmpty()) {
			List<Map<String, String>> viewer2DataList = list;
			for (int i = 0; i < viewer2DataList.size(); i++) {
				dataSource.add(viewer2DataList.get(i).get(headerList.get(0)),
				Integer.parseInt(viewer2DataList.get(i).get(headerList.get(1))));
			}
		}
		else {
			dataSource.add("-",0);
		}
		PieChartBuilder pieChart = cht.pieChart();
		pieChart.setShowValues(true);
		pieChart.setKey(objname);
		pieChart.series(cht.serie(objtype));						
		StyleBuilder titleStyle2 = stl.style(3).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setLeftPadding(10)
			.setBackgroundColor(Color.WHITE).setBorder(stl.pen1Point().setLineColor(Color.LIGHT_GRAY));
		report.setDataSource(dataSource).setColumnStyle(titleStyle2);
		report.summary(pieChart);
		return report;
	}				
	
	/**
	 * @param list
	 * @param headerList
	 * @return report
	 */
	public JasperReportBuilder createExecutedRulesTable(List<Map<String, String>> list, List<String> headerList) {
		JasperReportBuilder report = report();
		View.logger.info("header" + headerList.size());
		TextColumnBuilder<String> objname1 = col.column(headerList.get(0), "objectname1", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> objname2 = col.column(headerList.get(1), "objectname2", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> objname3 = col.column(headerList.get(2), "objectname3", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<String> objname4 = col.column(headerList.get(3), "objectname4", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		report.setPageFormat(PageType.B4);
		StyleBuilder titleStyle1 = stl.style(stl.style(3).bold()).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBackgroundColor(Color.LIGHT_GRAY)
			.setBorder(stl.pen1Point()).setLeftIndent(10);
		StyleBuilder titleStyle3 = stl.style(stl.style(3)).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
		report.columns(objname1.setStyle(titleStyle3), objname2.setStyle(titleStyle3),objname3.setStyle(titleStyle3),objname4.setStyle(titleStyle3)).setColumnTitleStyle(titleStyle1);

		DRDataSource dataSource = new DRDataSource("objectname1", "objectname2","objectname3","objectname4");
		View.logger.info(headerList.size()+"");
		if (!list.isEmpty()) {
			List<Map<String, String>> viewer2DataList = list;
			for (int i = 0; i < viewer2DataList.size(); i++) {
				dataSource.add(viewer2DataList.get(i).get(headerList.get(0)),
				viewer2DataList.get(i).get(headerList.get(1)),
				viewer2DataList.get(i).get(headerList.get(2)),
				viewer2DataList.get(i).get(headerList.get(3)));
			}
		}						
		StyleBuilder titleStyle2 = stl.style(3).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setLeftPadding(10)
			.setBackgroundColor(Color.WHITE).setBorder(stl.pen1Point().setLineColor(Color.LIGHT_GRAY));
		report.setDataSource(dataSource).setColumnStyle(titleStyle2);
		return report;
	}
				
	/**
	 * @param list
	 * @param headerList
	 * @return report
	 */
	public JasperReportBuilder createIncompatibleFeatureTable(List<Map<String, String>> list, List<String> headerList) {
		JasperReportBuilder report = report();
		View.logger.info("header" + headerList.size());
		TextColumnBuilder<String> objname1 = col.column(headerList.get(0), "objectname1", type.stringType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		TextColumnBuilder<Integer> objname2 = col.column(headerList.get(1), "objectname2", type.integerType())
			.setHorizontalAlignment(HorizontalAlignment.LEFT);
		report.setPageFormat(PageType.B4);
		StyleBuilder titleStyle1 = stl.style(stl.style(3).bold()).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBackgroundColor(Color.LIGHT_GRAY)
			.setBorder(stl.pen1Point()).setLeftIndent(10);
		StyleBuilder titleStyle3 = stl.style(stl.style(3)).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
		report.columns(objname1.setStyle(titleStyle3), objname2.setStyle(titleStyle3)).setColumnTitleStyle(titleStyle1);

		DRDataSource dataSource = new DRDataSource("objectname1", "objectname2");
		View.logger.info(headerList.size()+"");
		if (!list.isEmpty()) {
			List<Map<String, String>> viewer2DataList = list;
			for (int i = 0; i < viewer2DataList.size(); i++) {
				dataSource.add(viewer2DataList.get(i).get(headerList.get(0)),
				Integer.parseInt(viewer2DataList.get(i).get(headerList.get(1))));
			}
		}
						
		StyleBuilder titleStyle2 = stl.style(3).setFontSize(10)
			.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setLeftPadding(10)
			.setBackgroundColor(Color.WHITE).setBorder(stl.pen1Point().setLineColor(Color.LIGHT_GRAY));
		report.setDataSource(dataSource).setColumnStyle(titleStyle2);
		return report;
	}
				
	/**
	 * @param list
	 * @param colsList
	 * @return report
	 */
	public JasperReportBuilder createReport20(List<Map<String, String>> list, List<String> colsList) {
		JasperReportBuilder report = report();
		if (!list.isEmpty()) {
			View.logger.info("size" + list.size());
			List<String> headerList = colsList;
			List<Map<String, String>> viewer2DataList = list;
			View.logger.info("header" + headerList.size());
			TextColumnBuilder<String> objname = col.column(headerList.get(0), "objectname", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			TextColumnBuilder<String> objtype = col.column(headerList.get(1), "objecttype", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.LEFT);
			report.setPageFormat(PageType.B4);
			StyleBuilder titleStyle1 = stl.style(stl.style(3).bold()).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setBackgroundColor(Color.LIGHT_GRAY)
				.setBorder(stl.pen1Point()).setLeftIndent(10);
			StyleBuilder titleStyle4 = stl.style(stl.style(3).bold()).setFontSize(10)
				.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setForegroundColor(Color.BLACK)
				.setBorder(stl.pen1Point().setLineColor(Color.BLACK)).setLeftIndent(10);
			report.columns(objname.setStyle(titleStyle4), objtype.setStyle(titleStyle4)).setColumnTitleStyle(titleStyle1);

			DRDataSource dataSource = new DRDataSource("objectname", "objecttype");
				View.logger.info(colsList.size()+"");
				for (int i = 0; i < viewer2DataList.size(); i++) {							
					dataSource.add(viewer2DataList.get(i).get(headerList.get(0)),
					viewer2DataList.get(i).get(headerList.get(1)));
				}						 
				StyleBuilder titleStyle2 = stl.style(3).setFontSize(10)
					.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT).setLeftPadding(10)
					.setBackgroundColor(Color.WHITE).setBorder(stl.pen1Point().setLineColor(Color.LIGHT_GRAY));
				report.setDataSource(dataSource).setColumnStyle(titleStyle2);
		}
		return report;
	} 
}