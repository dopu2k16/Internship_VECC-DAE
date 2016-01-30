package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import javax.servlet.http.HttpSession;


import model.dao.OracleJDBC;

//import com.itextpdf.awt.geom.Rectangle;
//import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.Font.FontFamily;
//import com.itextpdf.text.Font.FontStyle;
import com.itextpdf.text.Rectangle;

/**
 * Servlet implementation class PaySlipPdfServlet
 */
@WebServlet("/PaySlipPdfServlet")
public class PaySlipPdfServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PaySlipPdfServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	// TODO Auto-generated method stub

	/**
	 *
	 * @author Mitodru Niyogi
	 */

	{
		HttpSession session = request.getSession(false);
		System.out.println((session.getAttribute("sperid").toString()));
		int perid = Integer.parseInt((String) session.getAttribute("sperid"));
		// if (session.getAttribute("status")=="login"){
		System.out.println("PERID :" + perid);
		int i1 = Integer.parseInt(request.getParameter("j"));
		int mm = 0, getmm = 0;
		int yyyy = 0, getyyyy = 0;
		int mon = 0;
		int year = 0;
		if (i1 == 0) {
			System.out.println("I am i1=0");
			Calendar cal = Calendar.getInstance();
			int dd = cal.get(Calendar.DAY_OF_MONTH);
			mm = cal.get(Calendar.MONTH) + 1;
			yyyy = cal.get(Calendar.YEAR);
			System.out.println("Date" + dd);
			System.out.println("Month: " + mm);
			System.out.println("Year: " + yyyy);
			if (dd < 26) {
				mm -= 1;

				if (mm == 0) {
					mm = 12;
					yyyy -= 1;
				}
				// else{
				// mm = mm - 1;
				// }

			}
		}

		System.out.println("Month: " + mm);
		System.out.println("Year: 2 " + yyyy);

		if (i1 == 1) {
			System.out.println("I am i1=1");
			getmm = Integer.parseInt(request.getParameter("month"));
			getyyyy = Integer.parseInt(request.getParameter("year"));
			Calendar cal = Calendar.getInstance();
			int dd = cal.get(Calendar.DAY_OF_MONTH);
			mon = cal.get(Calendar.MONTH) + 1;
			year = cal.get(Calendar.YEAR);
			if (getyyyy < year) {
				mm = getmm;
				yyyy = getyyyy;
				System.out.print(yyyy);
			} else if (getyyyy == year) {
				if (getmm < mon) {
					mm = getmm;
					yyyy = getyyyy;
				} else {
					mm = cal.get(Calendar.MONTH) + 1;
					yyyy = cal.get(Calendar.YEAR);
					if (dd < 26) {
						if (mm - 1 == 0) {
							mm = 12;
							yyyy -= 1;
						} else {
							mm = mm - 1;
						}
					}
				}
			} else {
				mm = cal.get(Calendar.MONTH) + 1;
				yyyy = cal.get(Calendar.YEAR);
				if (dd < 26) {
					if (mm - 1 == 0) {
						mm = 12;
						yyyy = yyyy - 1;
					} else {
						mm = mm - 1;
					}
					// yyyy = yyyy - 1;
				}
			}
		}
		int cctype = 0;
		int ccno = 0;
		
		response.setContentType("application/pdf");
		String fileName = "payslip.pdf";
		response.setHeader("Content-Disposition", "inline; filename=\""
				+ fileName + "\"");
		Document doc = new Document(PageSize.A4);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Connection conn = null;
		Statement stat = null;
		
		try {

			// PdfWriter.getInstance(doc,response.getOutputStream());
			PdfWriter.getInstance(doc, baos);

			BaseFont bf = BaseFont.createFont("c:/windows/fonts/DVBIYG3NT.TTF",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 12);
			doc.open();

			// Font bf12=new Font();

			Paragraph paragraph = new Paragraph(
					"ÊîîÏ»î ÖîÏ´øîÏ / Government of India\n", font);

			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph
					.add("ÇîÏËîîºîõ ¦â²îîì ïÒîÊîî¬î / Department of Atomic Energy\n");
			paragraph
					.add("ÇîïÏÒîñ»î ¦²îîì ÖÌî©Ñîîú¶êîÆî ´úøÆ¿è, ´øîúÑî´øî»îî/ VEC Centre Kolkata\n");
			paragraph.add("(Ñîúªî ÇîèÊîî¬î) / (Accounts Division)\n");

			conn = OracleJDBC.docon();
			stat = conn.createStatement();
			PreparedStatement CC_TYPE = conn
					.prepareStatement("SELECT  AE_CCTYPE,AE_CCNO FROM ADMIN.AD_CCNAME WHERE AE_PERID="
							+ perid);
			ResultSet rsc = CC_TYPE.executeQuery();
			while (rsc.next()) {
				cctype = rsc.getInt(1);
				ccno = rsc.getInt(2);
			}
			rsc.close();
			System.out.println("CCTYPE: " + cctype);
			System.out.println("CCNO: " + ccno);

			if (cctype == 119) {
				cctype = 820;
			}
			if (cctype == 139) {
				cctype = 821;
			}

			System.out.println("Year: " + yyyy);
			PreparedStatement select = conn
					.prepareStatement("SELECT ALL PAY_ROLL.PR_CCNO, PAY_ROLL.PR_CCTYPE, PAY_ROLL.PR_DESIG_254, PAY_ROLL.PR_DIV_256, PAY_ROLL.PR_NAME_251, PAY_ROLL.PR_PF_CODE_8, PAY_ROLL.PR_DESIG_CODE_5, PAY_ROLL.PR_DEBIT_HEAD_7, PAY_ROLL.PR_PF_ACCNO_9, PAY_ROLL.PR_BANK_CODE_12, PAY_ROLL.PR_BRANCH_CODE_13, PAY_ROLL.PR_BANK_ACCNO_41, PAY_ROLL.PR_EMP_NO_50, PAY_ROLL.PR_TOT_ENT_215, PAY_ROLL.PR_TOT_DED_216, PAY_ROLL.PR_NET_PAY_217, PAY_ROLL.PR_INC_PROG_280, PAY_ROLL.PR_PTAX_PROG_259, PAY_ROLL.PR_LIC_130, PAY_ROLL.PR_GROSS_PPF_212, PAY_ROLL.PR_NSC_NSS_218, PAY_ROLL.PR_NSC_INT_TOT_219, PAY_ROLL.PR_80C_DED_SUP_PAY_224, PAY_ROLL.PR_HBA_PRIN_IT_REB_227, PAY_ROLL.PR_CPF_GPF_PROG_281, PAY_ROLL.PR_IT_DED_SUP_PAY_220, PAY_ROLL.PR_IT_TOT_282, PAY_ROLL.PR_221, PAY_ROLL.PR_ITSC_TOT_283, PAY_ROLL.PR_GC_TOT_226, PAY_ROLL.PR_279, PAY_ROLL.PR_B_PAY_51, PAY_ROLL.PR_L_PAY_52, PAY_ROLL.PR_STAG_INC_53, PAY_ROLL.PR_SPL_PAY_54, PAY_ROLL.PR_P_PAY_55, PAY_ROLL.PR_SF_AL_56, PAY_ROLL.PR_DEP_AL_57, PAY_ROLL.PR_NPA_58, PAY_ROLL.PR_59, PAY_ROLL.PR_PGRAD_AL_60, PAY_ROLL.PR_DA_RATE_61, PAY_ROLL.PR_62, PAY_ROLL.PR_CCA_63, PAY_ROLL.PR_HRA_64, PAY_ROLL.PR_PROJ_AL_65, PAY_ROLL.PR_OT_TOT_66, PAY_ROLL.PR_TA_67, PAY_ROLL.PR_TRAN_AL_68, PAY_ROLL.PR_UNIF_AL_69, PAY_ROLL.PR_WASH_AL_70, PAY_ROLL.PR_LTC_71, PAY_ROLL.PR_HCA_72, PAY_ROLL.PR_QUAL_AL_73, PAY_ROLL.PR_CEA_74, PAY_ROLL.PR_PENSION_75, PAY_ROLL.PR_HON_76, PAY_ROLL.PR_NDA_77, PAY_ROLL.PR_BONUS_78, PAY_ROLL.PR_RTF_79, PAY_ROLL.PR_CLAIM_TOT_80, PAY_ROLL.PR_DA_AR_82, PAY_ROLL.PR_FEST_ADV_83, PAY_ROLL.PR_84, PAY_ROLL.PR_85, PAY_ROLL.PR_CPF_131, PAY_ROLL.PR_132, PAY_ROLL.PR_GPF_133, PAY_ROLL.PR_134, PAY_ROLL.PR_CHSS_REC_135, PAY_ROLL.PR_BUS_PASS_REC_136, PAY_ROLL.PR_ITAX_SC_138, PAY_ROLL.PR_ITAX_137, PAY_ROLL.PR_LIC_FEE_139, PAY_ROLL.PR_LIC_FEE_TAX_140, PAY_ROLL.PR_PROF_TAX_141, PAY_ROLL.PR_PLI_142, PAY_ROLL.PR_CTD_7_143, PAY_ROLL.PR_LIC_TOT_144, PAY_ROLL.PR_CGEIS_145, PAY_ROLL.PR_GRP_INS_146, PAY_ROLL.PR_COOP_SOC_147, PAY_ROLL.PR_FRS_148, PAY_ROLL.PR_VEH_REC_149, PAY_ROLL.PR_CGHS_REC_150, PAY_ROLL.PR_151, PAY_ROLL.PR_OUTSTA_REC_152, PAY_ROLL.PR_GROSS_DED_153, PAY_ROLL.PR_REL_FUND_155, PAY_ROLL.PR_RSTAMP_156, PAY_ROLL.PR_157, PAY_ROLL.PR_LIC_FEE_AR_158, PAY_ROLL.PR_LIC_FEE_TAX_AR_159, PAY_ROLL.PR_ARR_TOT_81, PAY_ROLL.PR_ADV_177, PAY_ROLL.PR_ADV_BAL_178, PAY_ROLL.PR_ADV_REC_RAT_179, PAY_ROLL.PR_ADV_CUR_INS_181, PAY_ROLL.PR_ADV_TOT_INS_180, PAY_ROLL.PR_ADV_182, PAY_ROLL.PR_ADV_BAL_183, PAY_ROLL.PR_ADV_REC_RAT_184, PAY_ROLL.PR_ADV_TOT_INS_185, PAY_ROLL.PR_ADV_CUR_INS_186, PAY_ROLL.PR_ADV_187, PAY_ROLL.PR_ADV_BAL_188, PAY_ROLL.PR_ADV_REC_RAT_189, PAY_ROLL.PR_ADV_TOT_INS_190, PAY_ROLL.PR_ADV_CUR_INS_191, PAY_ROLL.PR_ADV_192, PAY_ROLL.PR_ADV_BAL_193, PAY_ROLL.PR_ADV_REC_RAT_194, PAY_ROLL.PR_ADV_TOT_INS_195, PAY_ROLL.PR_ADV_CUR_INS_196, PAY_ROLL.PR_ADV_197, PAY_ROLL.PR_ADV_BAL_198, PAY_ROLL.PR_ADV_REC_RAT_199, PAY_ROLL.PR_ADV_TOT_INS_200, PAY_ROLL.PR_ADV_CUR_INS_201, PAY_ROLL.PR_ADV_202, PAY_ROLL.PR_ADV_BAL_203, PAY_ROLL.PR_ADV_REC_RAT_204, PAY_ROLL.PR_ADV_TOT_INS_205, PAY_ROLL.PR_ADV_CUR_INS_206, PAY_ROLL.PR_ADV_NAME_284, PAY_ROLL.PR_ADV_NAME_285, PAY_ROLL.PR_ADV_NAME_286, PAY_ROLL.PR_ADV_NAME_287, PAY_ROLL.PR_ADV_NAME_288, PAY_ROLL.PR_ADV_NAME_289, PAY_ROLL.PR_AR_NAME_290, PAY_ROLL.PR_AR_NAME_291, PAY_ROLL.PR_AR_NAME_292, PAY_ROLL.PR_AR_171, PAY_ROLL.PR_AR_INS_172, PAY_ROLL.PR_AR_173, PAY_ROLL.PR_AR_INS_174, PAY_ROLL.PR_AR_175, PAY_ROLL.PR_AR_INS_176, PAY_ROLL.PR_350,PAY_ROLL.PR_SAL_SUP_TOT_222,PAY_ROLL.PR_CSMA_95,PAY_ROLL.PR_PAY_ARAD_86  ,PAY_ROLL.PR_DA_ARAD_87,PAY_ROLL.PR_88,PAY_ROLL.PR_CCA_ARAD_89,PAY_ROLL.PR_HRA_ARAD_90,PAY_ROLL.PR_MISC_ARAD_91,PAY_ROLL.PR_92,PAY_ROLL.PR_MED_COST_93,PAY_ROLL.PR_LES_94,PAY_ROLL.PR_MED_BILL1_298, PAY_ROLL.PR_MED_BILL2_298, PAY_ROLL.PR_PF_TOT_BAL_347, PAY_ROLL.PR_MED_BILL3_299, PAY_ROLL.PR_MED_BILL4_299, PAY_ROLL.PR_MED_BILL_5_299,PAY_ROLL.PR_MED_BILL_6_299,PAY_ROLL.PR_MED_BILL_7_299,PAY_ROLL.PR_MED_BILL_8_299,PAY_ROLL.PR_MED_BILL_9_299,PAY_ROLL.PR_MED_BILL_10_299,PAY_ROLL.PR_MED_BILL_11_299,PAY_ROLL.PR_MED_BILL_12_299,PAY_ROLL.PR_MED_BILL_13_299,PAY_ROLL.PR_MED_BILL_14_299,PAY_ROLL.PR_MED_BILL_15_299,PAY_ROLL.PR_MED_CLM1_AMT,PAY_ROLL.PR_MED_CLM2_AMT,PAY_ROLL.PR_MED_CLM3_AMT,PAY_ROLL.PR_MED_CLM4_AMT,PAY_ROLL.PR_MED_CLM5_AMT,PAY_ROLL.PR_MED_CLM6_AMT,PAY_ROLL.PR_MED_CLM7_AMT, PAY_ROLL.PR_MED_CLM8_AMT, PAY_ROLL.PR_MED_CLM9_AMT, PAY_ROLL.PR_MED_CLM10_AMT, PAY_ROLL.PR_MED_CLM11_AMT, PAY_ROLL.PR_MED_CLM12_AMT, PAY_ROLL.PR_MED_CLM13_AMT, PAY_ROLL.PR_MED_CLM14_AMT ,PAY_ROLL.PR_MED_CLM15_AMT, PAY_ROLL.PR_NRPS_ALAD_210,PAY_ROLL.PR_275,PAY_ROLL.PR_274, PAY_ROLL.PR_PAN_NO_265, PAY_ROLL.PR_LOSS_CHSS_CARD_229, PAY_ROLL.PR_LOSS_I_CARD_230,PAY_ROLL.PR_LOSS_LIB_CARD_231,PAY_ROLL.PR_TEL_CHARGE_DED_232, PAY_ROLL.PR_ASS_SUB_DED_234,PAY_ROLL.PR_MISC_DED_238,PAY_ROLL.PR_TRUNC_CALL_DED_233, PAY_ROLL.PR_DENT_REC_237 FROM ACCT.PAY_ROLL, ACCT.PR_UNIT_CC WHERE  PAY_ROLL.PR_YYYY  = "
							+ yyyy
							+ " AND PAY_ROLL.PR_MM  =  "
							+ mm
							+ "  AND PAY_ROLL.PR_CCTYPE =  "
							+ cctype
							+ "   AND PAY_ROLL.PR_CCNO = "
							+ ccno
							+ "   AND  (PAY_ROLL.PR_PND_CODE_17 = 0  AND PR_UNIT_CC.PUC_UNIT = 'VECC'  AND PR_UNIT_CC.PUC_SUB_UNIT = 'VEC')  AND (PAY_ROLL.PR_CCTYPE = PR_UNIT_CC.PUC_CCTYPE) ORDER BY PAY_ROLL.PR_CCTYPE, PAY_ROLL.PR_CCNO");
			PreparedStatement CF_MON = conn
					.prepareStatement("SELECT to_char(to_date('01-'||to_char('"
							+ mm
							+ "','00')||'-2013','DD-MM-YYYY'),'MONth') FROM DUAL");
			PreparedStatement CF_YYYY = conn
					.prepareStatement("SELECT to_char('" + yyyy
							+ "','0000') FROM DUAL");
			PreparedStatement CF_CC = conn
					.prepareStatement("SELECT 'VC/'||to_char('" + cctype
							+ "')||'/'||to_char('" + ccno
							+ "','9999') FROM DUAL");

			ResultSet rs = CF_MON.executeQuery();
			ResultSet rs1 = CF_YYYY.executeQuery();
			while (rs.next() && rs1.next()) {
				paragraph
						.add(" Òîú»îÆî ïÒîÒîÏºî Çî½î´ø ËîîÙ /Pay  Bill  for  the  Month  of  "
								+ rs.getString(1) + rs1.getString(1) + "\n");
			}
			rs.close();
			rs1.close();
			// paragraph.add("----------------------------------------------------------------------------------------------------------------------------------\n");
			doc.add(paragraph);
			doc.add(new LineSeparator(0.5f, 100, null, 0, -5));
			float[] widths1 = { 2, 6, 2, 4 };
			PdfPTable table = new PdfPTable(widths1);
			table.setWidthPercentage(100);
			PdfPCell cell = new PdfPCell();

			Paragraph paragraph1 = new Paragraph();
			paragraph1.setAlignment(Element.ALIGN_LEFT);
			rs = CF_CC.executeQuery();
			while (rs.next()) {
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(15);
				cell.setPhrase(new Phrase("´øËîì°îîÏó ´èøËî / CC NO.", font/*
																			 * new
																			 * Font
																			 * (
																			 * Font
																			 * .
																			 * getFamily
																			 * (
																			 * getServletInfo
																			 * (
																			 * )
																			 * )
																			 * ,
																			 * 10
																			 * ,
																			 * Font
																			 * .
																			 * NORMAL
																			 */));
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase(rs.getString(1), font));
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);
			}
			rs.close();
			rs1 = select.executeQuery();
			while (rs1.next()) {
				if ("1".equals(rs1.getString("PR_PF_CODE_8"))) {
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPhrase(new Phrase(
							"Öîó.Çîó.§öø./²îó.Çîó.§ö.. Ñîúªîî Öîë.  GPF_ACC",
							font));
					cell.setBorder(Rectangle.NO_BORDER);
					table.addCell(cell);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPhrase(new Phrase(rs1.getString("PR_PF_ACCNO_9"),
							font));
					cell.setBorder(Rectangle.NO_BORDER);
					table.addCell(cell);
				} else if ("2".equals(rs1.getString("PR_PF_CODE_8"))) {
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPhrase(new Phrase("CPF_ACC", font));
					cell.setBorder(Rectangle.NO_BORDER);
					table.addCell(cell);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPhrase(new Phrase(rs1.getString("PR_PF_ACCNO_9"),
							font));
					cell.setBorder(Rectangle.NO_BORDER);
					table.addCell(cell);
				} else {
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPhrase(new Phrase("NO_PF", font));
					cell.setBorder(Rectangle.NO_BORDER);
					table.addCell(cell);
				}
				table.completeRow();
				doc.add(table);
				float[] widths = { 2, 6, 2, 1, 3 };
				PdfPTable table1 = new PdfPTable(widths);
				table1.setWidthPercentage(100);
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase("ÆîîËî / NAME", font));
				cell.setBorder(Rectangle.NO_BORDER);
				table1.addCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase(rs1.getString("PR_NAME_251"), font));
				cell.setBorder(Rectangle.NO_BORDER);
				table1.addCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase("Çî¿ÆîîËî / DESG", font));
				cell.setBorder(Rectangle.NO_BORDER);
				table1.addCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase(rs1.getString("PR_DESIG_254"), font));
				cell.setBorder(Rectangle.NO_BORDER);
				table1.addCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase(rs1.getString("PR_DESIG_CODE_5"),
						font));
				cell.setBorder(Rectangle.NO_BORDER);
				table1.addCell(cell);
				table1.completeRow();
				doc.add(table1);
				PdfPTable table2 = new PdfPTable(widths1);
				table2.setWidthPercentage(100);
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase("ÇîèÊîî¬î / DIVISION", font));
				cell.setBorder(Rectangle.NO_BORDER);
				table2.addCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase(rs1.getString("PR_DIV_256"), font));
				cell.setBorder(Rectangle.NO_BORDER);
				table2.addCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase("ÆîîËîú ÓîóÕîì / DR.HD.", font));
				cell.setBorder(Rectangle.NO_BORDER);
				table2.addCell(cell);
				PreparedStatement CF_DEBIT_HEAD = conn
						.prepareStatement("SELECT PDH_NAME FROM ACCT.PR_DEBIT_HEADS where PDH_UNIT = 'VECC' and PDH_ID = "
								+ rs1.getString("PR_DEBIT_HEAD_7"));
				rs = CF_DEBIT_HEAD.executeQuery();
				while (rs.next()) {
					if ("VEC-3571".equals(rs.getString(1))) {
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPhrase(new Phrase("3401-403", font));
						cell.setBorder(Rectangle.NO_BORDER);
						table2.addCell(cell);
					} else {
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPhrase(new Phrase(rs.getString(1), font));
						cell.setBorder(Rectangle.NO_BORDER);
						table2.addCell(cell);
					}
				}
				table2.completeRow();
				doc.add(table2);
				rs.close();
				// paragraph1.add("----------------------------------------------------------------------------------------------------------------------------------\n");
				doc.add(paragraph1);
				doc.add(new LineSeparator(0.5f, 100, null, 0, -5));

				PreparedStatement CF_ENT = conn.prepareStatement("SELECT" + " "

				+ "acct.PR_ENT_TAG_HND(1," + rs1.getString("PR_B_PAY_51") + ")"

				+ "||"

				+ "acct.PR_ENT_TAG_HND(2," + rs1.getString("PR_L_PAY_52") + ")"

				+ "||"

				+ "acct.PR_ENT_TAG_HND(3," + rs1.getString("PR_STAG_INC_53")
						+ ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(4,"
						+ rs1.getString("PR_SPL_PAY_54") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(5,"
						+ rs1.getString("PR_P_PAY_55") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(6,"
						+ rs1.getString("PR_SF_AL_56") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(7,"
						+ rs1.getString("PR_DEP_AL_57") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(8," + rs1.getString("PR_NPA_58")
						+ ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(9," + rs1.getString("PR_59")
						+ ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(10,"
						+ rs1.getString("PR_PGRAD_AL_60") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(12,"
						+ rs1.getString("PR_DA_RATE_61") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(13," + rs1.getString("PR_62")
						+ ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(15,"
						+ rs1.getString("PR_CCA_63") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(16,"
						+ rs1.getString("PR_HRA_64") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(17,"
						+ rs1.getString("PR_PROJ_AL_65") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(18,"
						+ rs1.getString("PR_NRPS_ALAD_210") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(19,"
						+ rs1.getString("PR_OT_TOT_66") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(20," + rs1.getString("PR_TA_67")
						+ ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(21,"
						+ rs1.getString("PR_TRAN_AL_68") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(22,"
						+ rs1.getString("PR_UNIF_AL_69") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(23,"
						+ rs1.getString("PR_WASH_AL_70") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(24,"
						+ rs1.getString("PR_LTC_71") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(25,"
						+ rs1.getString("PR_HCA_72") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(26,"
						+ rs1.getString("PR_QUAL_AL_73") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(27,"
						+ rs1.getString("PR_CEA_74") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(28,"
						+ rs1.getString("PR_PENSION_75") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(30,"
						+ rs1.getString("PR_HON_76") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(31,"
						+ rs1.getString("PR_NDA_77") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(32,"
						+ rs1.getString("PR_BONUS_78") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(33,"
						+ rs1.getString("PR_RTF_79") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(36,"
						+ rs1.getString("PR_ARR_TOT_81") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(37,"
						+ rs1.getString("PR_DA_AR_82") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(38,"
						+ rs1.getString("PR_FEST_ADV_83") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(39," + rs1.getString("PR_84")
						+ ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(40," + rs1.getString("PR_85")
						+ ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(41,"
						+ rs1.getString("PR_MED_BILL1_298") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(42,"
						+ rs1.getString("PR_MED_BILL2_298") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(43,"
						+ rs1.getString("PR_MED_BILL3_299") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(44,"
						+ rs1.getString("PR_MED_BILL4_299") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(45,"
						+ rs1.getString("PR_MED_BILL_5_299") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(46,"
						+ rs1.getString("PR_MED_BILL_6_299") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(47,"
						+ rs1.getString("PR_MED_BILL_7_299") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(48,"
						+ rs1.getString("PR_MED_BILL_8_299") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(49,"
						+ rs1.getString("PR_MED_BILL_9_299") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(50,"
						+ rs1.getString("PR_MED_BILL_10_299") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(51,"
						+ rs1.getString("PR_MED_BILL_11_299") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(52,"
						+ rs1.getString("PR_MED_BILL_12_299") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(53,"
						+ rs1.getString("PR_MED_BILL_13_299") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(54,"
						+ rs1.getString("PR_MED_BILL_14_299") + ")"

						+ "||"

						+ "acct.PR_ENT_TAG_HND(55,"
						+ rs1.getString("PR_MED_BILL_15_299") + ")"

						+ " " + "FROM DUAL");

				PreparedStatement CF_ENT_AMT = conn.prepareStatement("SELECT"
						+ " "

						+ "ACCT.PR_ENT_TAG_AMT(1,"
						+ rs1.getString("PR_B_PAY_51")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(2,"
						+ rs1.getString("PR_L_PAY_52")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(3,"
						+ rs1.getString("PR_STAG_INC_53")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(4,"
						+ rs1.getString("PR_SPL_PAY_54")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(5,"
						+ rs1.getString("PR_P_PAY_55")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(6,"
						+ rs1.getString("PR_SF_AL_56")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(7,"
						+ rs1.getString("PR_DEP_AL_57")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(8,"
						+ rs1.getString("PR_NPA_58")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(9,"
						+ rs1.getString("PR_59")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(10,"
						+ rs1.getString("PR_PGRAD_AL_60")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(12,"
						+ rs1.getString("PR_DA_RATE_61")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(13,"
						+ rs1.getString("PR_62")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(15,"
						+ rs1.getString("PR_CCA_63")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(16,"
						+ rs1.getString("PR_HRA_64")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(17,"
						+ rs1.getString("PR_PROJ_AL_65")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(18,"
						+ rs1.getString("PR_NRPS_ALAD_210")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(19,"
						+ rs1.getString("PR_OT_TOT_66")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(20,"
						+ rs1.getString("PR_TA_67")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(21,"
						+ rs1.getString("PR_TRAN_AL_68")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(22,"
						+ rs1.getString("PR_UNIF_AL_69")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(23,"
						+ rs1.getString("PR_WASH_AL_70")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(24,"
						+ rs1.getString("PR_LTC_71")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(25,"
						+ rs1.getString("PR_HCA_72")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(26,"
						+ rs1.getString("PR_QUAL_AL_73")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(27,"
						+ rs1.getString("PR_CEA_74")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(28,"
						+ rs1.getString("PR_PENSION_75")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(30,"
						+ rs1.getString("PR_HON_76")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(31,"
						+ rs1.getString("PR_NDA_77")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(32,"
						+ rs1.getString("PR_BONUS_78")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(33,"
						+ rs1.getString("PR_RTF_79")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(36,"
						+ rs1.getString("PR_ARR_TOT_81")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(37,"
						+ rs1.getString("PR_DA_AR_82")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(38,"
						+ rs1.getString("PR_FEST_ADV_83")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(39,"
						+ rs1.getString("PR_84")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(40,"
						+ rs1.getString("PR_85")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(41,"
						+ rs1.getString("PR_MED_BILL1_298")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(42,"
						+ rs1.getString("PR_MED_BILL2_298")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(43,"
						+ rs1.getString("PR_MED_BILL3_299")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(44,"
						+ rs1.getString("PR_MED_BILL4_299")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(45,"
						+ rs1.getString("PR_MED_BILL_5_299")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(46,"
						+ rs1.getString("PR_MED_BILL_6_299")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(47,"
						+ rs1.getString("PR_MED_BILL_7_299")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(48,"
						+ rs1.getString("PR_MED_BILL_8_299")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(49,"
						+ rs1.getString("PR_MED_BILL_9_299")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(50,"
						+ rs1.getString("PR_MED_BILL_10_299")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(51,"
						+ rs1.getString("PR_MED_BILL_11_299")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(52,"
						+ rs1.getString("PR_MED_BILL_12_299")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(53,"
						+ rs1.getString("PR_MED_BILL_13_299")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(54,"
						+ rs1.getString("PR_MED_BILL_14_299")
						+ ")"

						+ "||"

						+ "ACCT.PR_ENT_TAG_AMT(55,"
						+ rs1.getString("PR_MED_BILL_15_299") + ")"

						+ " " + "FROM DUAL");

				PreparedStatement CF_DED_N = conn.prepareStatement("SELECT "
						+ "ACCT.PR_DED_TAG_HND(1,"
						+ rs1.getString("PR_CPF_131")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(2,"
						+ rs1.getString("PR_132")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(3,"
						+ rs1.getString("PR_GPF_133")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(4,"
						+ rs1.getString("PR_134")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(5,"
						+ rs1.getString("PR_CHSS_REC_135")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(6,"
						+ rs1.getString("PR_BUS_PASS_REC_136")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(7,"
						+ rs1.getString("PR_ITAX_137")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(8,"
						+ rs1.getString("PR_ITAX_SC_138")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(9,"
						+ rs1.getString("PR_LIC_FEE_139")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(10,"
						+ rs1.getString("PR_LIC_FEE_TAX_140")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(11,"
						+ rs1.getString("PR_PROF_TAX_141")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(12,"
						+ rs1.getString("PR_PLI_142")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(13,"
						+ rs1.getString("PR_CTD_7_143")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(14,"
						+ rs1.getString("PR_LIC_TOT_144")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(15,"
						+ rs1.getString("PR_CGEIS_145")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(16,"
						+ rs1.getString("PR_GRP_INS_146")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(17,"
						+ rs1.getString("PR_COOP_SOC_147")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(18,"
						+ rs1.getString("PR_FRS_148")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(19,"
						+ rs1.getString("PR_VEH_REC_149")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(20,"
						+ rs1.getString("PR_CGHS_REC_150")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(21,"
						+ rs1.getString("PR_151")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(22,"
						+ rs1.getString("PR_OUTSTA_REC_152")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(25,"
						+ rs1.getString("PR_REL_FUND_155")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(26,"
						+ rs1.getString("PR_RSTAMP_156")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(27,"
						+ rs1.getString("PR_157")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(28,"
						+ rs1.getString("PR_LIC_FEE_AR_158")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(29,"
						+ rs1.getString("PR_LIC_FEE_TAX_AR_159")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(30,"
						+ rs1.getString("PR_LOSS_CHSS_CARD_229")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(31,"
						+ rs1.getString("PR_LOSS_I_CARD_230")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(32,"
						+ rs1.getString("PR_TEL_CHARGE_DED_232")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(34,"
						+ rs1.getString("PR_TRUNC_CALL_DED_233")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(35,"
						+ rs1.getString("PR_ASS_SUB_DED_234")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(36,"
						+ rs1.getString("PR_MISC_DED_238")
						+ ")"

						+ "||"

						+ "ACCT.PR_DED_TAG_HND(37,"
						+ rs1.getString("PR_DENT_REC_237") + ")"

						+ " " + "FROM DUAL");

				PreparedStatement CF_DED_AMT = conn.prepareStatement("SELECT "
						+ "acct.PR_DED_TAG_AMT(1,"
						+ rs1.getString("PR_CPF_131")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(2,"
						+ rs1.getString("PR_132")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(3,"
						+ rs1.getString("PR_GPF_133")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(4,"
						+ rs1.getString("PR_134")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(5,"
						+ rs1.getString("PR_CHSS_REC_135")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(6,"
						+ rs1.getString("PR_BUS_PASS_REC_136")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(7,"
						+ rs1.getString("PR_ITAX_137")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(8,"
						+ rs1.getString("PR_ITAX_SC_138")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(9,"
						+ rs1.getString("PR_LIC_FEE_139")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(10,"
						+ rs1.getString("PR_LIC_FEE_TAX_140")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(11,"
						+ rs1.getString("PR_PROF_TAX_141")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(12,"
						+ rs1.getString("PR_PLI_142")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(13,"
						+ rs1.getString("PR_CTD_7_143")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(14,"
						+ rs1.getString("PR_LIC_TOT_144")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(15,"
						+ rs1.getString("PR_CGEIS_145")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(16,"
						+ rs1.getString("PR_GRP_INS_146")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(17,"
						+ rs1.getString("PR_COOP_SOC_147")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(18,"
						+ rs1.getString("PR_FRS_148")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(19,"
						+ rs1.getString("PR_VEH_REC_149")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(20,"
						+ rs1.getString("PR_CGHS_REC_150")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(21,"
						+ rs1.getString("PR_151")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(22,"
						+ rs1.getString("PR_OUTSTA_REC_152")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(25,"
						+ rs1.getString("PR_REL_FUND_155")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(26,"
						+ rs1.getString("PR_RSTAMP_156")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(27,"
						+ rs1.getString("PR_157")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(28,"
						+ rs1.getString("PR_LIC_FEE_AR_158")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(29,"
						+ rs1.getString("PR_LIC_FEE_TAX_AR_159")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(30,"
						+ rs1.getString("PR_LOSS_CHSS_CARD_229")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(31,"
						+ rs1.getString("PR_LOSS_I_CARD_230")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(33,"
						+ rs1.getString("PR_TEL_CHARGE_DED_232")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(34,"
						+ rs1.getString("PR_TRUNC_CALL_DED_233")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(35,"
						+ rs1.getString("PR_ASS_SUB_DED_234")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(36,"
						+ rs1.getString("PR_MISC_DED_238")
						+ ")"
						+ "||"
						+ "acct.PR_DED_TAG_AMT(37,"
						+ rs1.getString("PR_DENT_REC_237")
						+ ")"
						+ " "
						+ "FROM DUAL");

				PreparedStatement CF_DED = conn.prepareStatement("SELECT "
						+ "acct.PR_DED_ADV_TAG_HND('"
						+ rs1.getString("PR_ADV_NAME_284")
						+ "', '"
						+ rs1.getString("PR_ADV_REC_RAT_179")
						+ "', '"
						+ rs1.getString("PR_ADV_177")
						+ "', '"
						+ rs1.getString("PR_ADV_BAL_178")
						+ "', '"
						+ rs1.getString("PR_ADV_CUR_INS_181")
						+ "', '"
						+ rs1.getString("PR_ADV_TOT_INS_180")
						+ "')"
						+ "||"
						+ "acct.PR_DED_ADV_TAG_HND('"
						+ rs1.getString("PR_ADV_NAME_285")
						+ "', '"
						+ rs1.getString("PR_ADV_REC_RAT_184")
						+ "', '"
						+ rs1.getString("PR_ADV_182")
						+ "', '"
						+ rs1.getString("PR_ADV_BAL_183")
						+ "', '"
						+ rs1.getString("PR_ADV_CUR_INS_186")
						+ "', '"
						+ rs1.getString("PR_ADV_TOT_INS_185")
						+ "')"
						+ "||"
						+ "acct.PR_DED_ADV_TAG_HND('"
						+ rs1.getString("PR_ADV_NAME_286")
						+ "', '"
						+ rs1.getString("PR_ADV_REC_RAT_189")
						+ "', '"
						+ rs1.getString("PR_ADV_187")
						+ "', '"
						+ rs1.getString("PR_ADV_BAL_188")
						+ "', '"
						+ rs1.getString("PR_ADV_CUR_INS_191")
						+ "', '"
						+ rs1.getString("PR_ADV_TOT_INS_190")
						+ "')"
						+ "||"
						+ "acct.PR_DED_ADV_TAG_HND('"
						+ rs1.getString("PR_ADV_NAME_287")
						+ "', '"
						+ rs1.getString("PR_ADV_REC_RAT_194")
						+ "', '"
						+ rs1.getString("PR_ADV_192")
						+ "', '"
						+ rs1.getString("PR_ADV_BAL_193")
						+ "', '"
						+ rs1.getString("PR_ADV_CUR_INS_196")
						+ "', '"
						+ rs1.getString("PR_ADV_TOT_INS_195")
						+ "')"
						+ "||"
						+ "acct.PR_DED_ADV_TAG_HND('"
						+ rs1.getString("PR_ADV_NAME_288")
						+ "', '"
						+ rs1.getString("PR_ADV_REC_RAT_199")
						+ "', '"
						+ rs1.getString("PR_ADV_197")
						+ "', '"
						+ rs1.getString("PR_ADV_BAL_198")
						+ "', '"
						+ rs1.getString("PR_ADV_CUR_INS_201")
						+ "', '"
						+ rs1.getString("PR_ADV_TOT_INS_200")
						+ "')"
						+ "||"
						+ "acct.PR_DED_ADV_TAG_HND('"
						+ rs1.getString("PR_ADV_NAME_289")
						+ "', '"
						+ rs1.getString("PR_ADV_REC_RAT_204")
						+ "', '"
						+ rs1.getString("PR_ADV_202")
						+ "', '"
						+ rs1.getString("PR_ADV_BAL_203")
						+ "', '"
						+ rs1.getString("PR_ADV_CUR_INS_206")
						+ "', '"
						+ rs1.getString("PR_ADV_TOT_INS_205")
						+ "')"
						+ "||"
						+ "acct.PR_DED_AR_TAG_HND('"
						+ rs1.getString("PR_AR_NAME_290")
						+ "',"
						+ rs1.getString("PR_AR_INS_172")
						+ ","
						+ rs1.getString("PR_AR_171")
						+ ")"
						+ "||"
						+ "acct.PR_DED_AR_TAG_HND('"
						+ rs1.getString("PR_AR_NAME_291")
						+ "',"
						+ rs1.getString("PR_AR_INS_174")
						+ ","
						+ rs1.getString("PR_AR_173")
						+ ")"
						+ "||"
						+ "acct.PR_DED_AR_TAG_HND('"
						+ rs1.getString("PR_AR_NAME_292")
						+ "',"
						+ rs1.getString("PR_AR_INS_176")
						+ ","
						+ rs1.getString("PR_AR_175") + ")" + " " + "FROM DUAL");

				float[] widths2 = { 7, 3, 5, 2, 8 };
				PdfPTable tab = new PdfPTable(widths2);
				tab.setWidthPercentage(100);
				PdfPCell c1 = new PdfPCell(new Phrase(
						("¤ïÅî´ùøï»îÌîîë / Entitlement "), font));
				c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				c1.setBorder(Rectangle.NO_BORDER);
				tab.addCell(c1);
				c1 = new PdfPCell(new Phrase(("Ðø. / Rs. "), font));
				c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				c1.setBorder(Rectangle.NO_BORDER);
				tab.addCell(c1);
				c1 = new PdfPCell(new Phrase(("´ø¶îûüï»îÌîîë / Deductions"),
						font));
				c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				c1.setBorder(Rectangle.NO_BORDER);
				tab.addCell(c1);
				c1 = new PdfPCell(new Phrase(("Ðø. / Rs."), font));
				c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				c1.setBorder(Rectangle.NO_BORDER);
				tab.addCell(c1);
				c1 = new PdfPCell(
						new Phrase(
								("ï´øÖ»î. / Instal  ´õøÑî / Total  ÓîîúÕî / Bal"),
								font));
				c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				c1.setBorder(Rectangle.NO_BORDER);
				tab.addCell(c1);
				tab.completeRow();
				doc.add(tab);
				Paragraph paragraph2 = new Paragraph();
				// paragraph2.add("----------------------------------------------------------------------------------------------------------------------------------\n");
				doc.add(paragraph2);
				doc.add(new LineSeparator(0.5f, 100, null, 0, -5));

				// float[] idths2 = { 6, 5, 3, 2, 8};

				PdfPTable tab1 = new PdfPTable(widths2);
				tab1.setWidths(widths2);
				tab1.setWidthPercentage(100);
				rs = CF_ENT.executeQuery();
				ResultSet rsi = CF_ENT_AMT.executeQuery();
				cell = new PdfPCell();
				ResultSet rsii = CF_DED_N.executeQuery();
				ResultSet rsiii = CF_DED_AMT.executeQuery();

				while (rs.next() && rsi.next()) {
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPhrase(new Phrase(rs.getString(1), font));
					// cell.setIndent(10);
					cell.setBorder(Rectangle.NO_BORDER);
					tab1.addCell(cell);
					//cell.setColspan(1);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(rsi.getString(1), font));
					cell.setBorder(Rectangle.NO_BORDER);
					tab1.addCell(cell);
				}
				cell = new PdfPCell();
				while (rsii.next() && rsiii.next()) {
					// cell.setSpaceCharRatio(10);
					// cell.setPaddingRight(10);
					// cell.setIndent(10);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPhrase(new Phrase(rsii.getString(1), font));

					// cell.setLeading(20f, 0f);
					cell.setBorder(Rectangle.NO_BORDER);
					tab1.addCell(cell);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(rsiii.getString(1), font));
					// cell.setBorderWidthBottom(5);
					// cell.setColspan(2);
					// cell.setPaddingBottom(5);
					// cell.setBorderWidthLeft(10);
					// cell.setSpaceCharRatio(10);
					// cell.setLeading(20f, 0f);
					//cell.setRightIndent(10);
					cell.setBorder(Rectangle.NO_BORDER);
					tab1.addCell(cell);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase("\n ", font));
					// cell.setBorderWidthLeft(10);
					// cell.setSpaceCharRatio(10);
					cell.setRightIndent(10);
					cell.setBorder(Rectangle.NO_BORDER);
					tab1.addCell(cell);
				}
				tab1.completeRow();
				doc.add(tab1);
				rs.close();
				rsi.close();
				rsii.close();
				rsiii.close();

				float[] widths5 = { 6, 5, 13 };
				PdfPTable tab10 = new PdfPTable(widths5);
				tab10.setWidthPercentage(100);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase(" ", font));
				cell.setBorder(Rectangle.NO_BORDER);
				tab10.addCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPhrase(new Phrase(" ", font));
				cell.setBorder(Rectangle.NO_BORDER);
				tab10.addCell(cell);
				ResultSet rsiv = CF_DED.executeQuery();
				while (rsiv.next()) {
					// cell.setLeading(20f, 0f);
					//cell.setSpaceCharRatio(0);
					// cell.setRightIndent(10);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPhrase(new Phrase(rsiv.getString(1), font));
					cell.setBorder(Rectangle.NO_BORDER);
					tab10.addCell(cell);
				}
				tab10.completeRow();
				doc.add(tab10);
				rsiv.close();

				// ResultSet rsi;
				Paragraph paragraph3 = new Paragraph();
				// paragraph3.add("----------------------------------------------------------------------------------------------------------------------------------\n");
				doc.add(paragraph3);
				doc.add(new LineSeparator(0.5f, 100, null, 0, -5));
				float[] widths3 = { 5, 3, 5, 4, 5, 5 };
				PdfPTable tab2 = new PdfPTable(widths3);
				tab2.setWidthPercentage(100);
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase("´õøÑî ¤ïÅî´ùøï»îÌîîë / GR. ENT",
						font));
				cell.setBorder(Rectangle.NO_BORDER);
				tab2.addCell(cell);

				rsi = stat.executeQuery("SELECT TO_CHAR("
						+ rs1.getString("PR_TOT_ENT_215")
						+ ",'9,99,99,999') FROM DUAL");
				while (rsi.next()) {
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(rsi.getString(1).toUpperCase(),
							new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					tab2.addCell(cell);
				}
				rsi.close();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase("´õøÑî ´ø¶îûüï»îÌîîë / GR. DED", font));
				cell.setBorder(Rectangle.NO_BORDER);
				tab2.addCell(cell);
				rsi = stat.executeQuery("SELECT TO_CHAR("
						+ rs1.getString("PR_TOT_DED_216")
						+ ",'9,99,99,999') FROM DUAL");
				while (rsi.next()) {
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(rsi.getString(1), font));
					cell.setBorder(Rectangle.NO_BORDER);
					tab2.addCell(cell);
				}
				rsi.close();
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPhrase(new Phrase(" ", font));
				cell.setBorder(Rectangle.NO_BORDER);
				tab2.addCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPhrase(new Phrase(" ", font));
				cell.setBorder(Rectangle.NO_BORDER);
				tab2.addCell(cell);
				tab2.completeRow();
				doc.add(tab2);

				PdfPTable tab3 = new PdfPTable(widths3);
				tab3.setWidthPercentage(100);
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase("ÓîõÁ Òîú»îÆî / NET PAY  ", font));
				cell.setBorder(Rectangle.NO_BORDER);
				tab3.addCell(cell);

				rsi = stat.executeQuery("SELECT TO_CHAR("
						+ rs1.getString("PR_NET_PAY_217")
						+ ",'9,99,99,999') FROM DUAL");
				while (rsi.next()) {
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(rsi.getString(1), font));
					cell.setBorder(Rectangle.NO_BORDER);
					tab3.addCell(cell);

				}
				rsi.close();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase("Éîûü´ø Óîîªîî / BANK-BRANCH", font));

				cell.setBorder(Rectangle.NO_BORDER);
				tab3.addCell(cell);

				rsi = stat.executeQuery("SELECT TO_CHAR("
						+ rs1.getString("PR_BANK_CODE_12") + ")||'/'||TO_CHAR("
						+ rs1.getString("PR_BRANCH_CODE_13") + ") FROM DUAL");
				while (rsi.next()) {
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(rsi.getString(1), font));
					cell.setBorder(Rectangle.NO_BORDER);
					tab3.addCell(cell);

				}
				rsi.close();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase("Éîûü´ø Ñîúªîî Öîë. AC-NO", font));
				cell.setBorder(Rectangle.NO_BORDER);
				tab3.addCell(cell);

				rsi = stat.executeQuery("SELECT '"
						+ rs1.getString("PR_BANK_ACCNO_41") + "' FROM DUAL");
				while (rsi.next()) {
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPhrase(new Phrase(rsi.getString(1), font));
					cell.setBorder(Rectangle.NO_BORDER);
					tab3.addCell(cell);
				}
				rsi.close();
				tab3.completeRow();
				doc.add(tab3);

				// ResultSet rsii,rsiii;

				Paragraph paragraph4 = new Paragraph();
				// paragraph4.add("----------------------------------------------------------------------------------------------------------------------------------\n");
				doc.add(paragraph4);
				doc.add(new LineSeparator(0.5f, 100, null, 0, -5));
				PdfPTable tab4 = new PdfPTable(6);
				tab4.setWidthPercentage(100);
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase("´õøÑî ¤îÌî / GROSS INCOME", font));
				cell.setBorder(Rectangle.NO_BORDER);
				tab4.addCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPhrase(new Phrase("ÒÌîÒîÖîîÌî ´øÏ / E TAX", font));
				cell.setBorder(Rectangle.NO_BORDER);
				tab4.addCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPhrase(new Phrase("80 Öîó. / TOT-80C", font));
				cell.setBorder(Rectangle.NO_BORDER);
				tab4.addCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPhrase(new Phrase("¤îÌî´øÏ / I-TAX", font));
				cell.setBorder(Rectangle.NO_BORDER);
				tab4.addCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPhrase(new Phrase("ïÓîàîî ¦Çî´øÏ / EDU.CESS  ", font));

				cell.setBorder(Rectangle.NO_BORDER);
				tab4.addCell(cell);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPhrase(new Phrase("´õøÑî ÇîïÏÑîïÉÅîÌîîë/GC-EML  ", font));
				cell.setBorder(Rectangle.NO_BORDER);
				tab4.addCell(cell);
				tab4.completeRow();
				doc.add(tab4);
				PdfPTable tab14 = new PdfPTable(6);
				tab14.setWidthPercentage(100);
				cell = new PdfPCell();
				rsi = stat.executeQuery("SELECT TO_CHAR(NVL("
						+ rs1.getString("PR_INC_PROG_280") + ",0)+NVL("
						+ rs1.getString("PR_SAL_SUP_TOT_222")
						+ ",0),'9,99,99,999') FROM DUAL");
				while (rsi.next()) {
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPhrase(new Phrase(rsi.getString(1), font));
					cell.setBorder(Rectangle.NO_BORDER);
					tab14.addCell(cell);
				}
				rsi.close();
				rsi = stat.executeQuery("SELECT TO_CHAR("
						+ rs1.getString("PR_PTAX_PROG_259")
						+ ",'9,99,99,999') FROM DUAL");
				while (rsi.next()) {
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPhrase(new Phrase(rsi.getString(1), font));
					cell.setBorder(Rectangle.NO_BORDER);
					tab14.addCell(cell);
				}
				rsi.close();
				rsi = stat.executeQuery("SELECT " + "NVL("
						+ rs1.getString("PR_LIC_130") + ",0)+" + "NVL("
						+ rs1.getString("PR_GROSS_PPF_212") + ",0)+" + "NVL("
						+ rs1.getString("PR_NSC_NSS_218") + ",0)+" + "NVL("
						+ rs1.getString("PR_NSC_INT_TOT_219") + ",0)+" + "NVL("
						+ rs1.getString("PR_80C_DED_SUP_PAY_224") + ",0)+"
						+ "NVL(" + rs1.getString("PR_HBA_PRIN_IT_REB_227")
						+ ",0)+" + "NVL("
						+ rs1.getString("PR_CPF_GPF_PROG_281") + ",0)+"
						+ "NVL(" + rs1.getString("PR_350") + ",0) FROM DUAL");
				while (rsi.next()) {
					if (Integer.parseInt(rsi.getString(1)) > 100000) {
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPhrase(new Phrase("1,00,000", font));
						cell.setBorder(Rectangle.NO_BORDER);
						tab14.addCell(cell);

					} else {
						rsii = stat.executeQuery("SELECT TO_CHAR("
								+ rsi.getString(1)
								+ ",'9,99,99,999') FROM DUAL");
						while (rsii.next()) {
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPhrase(new Phrase(rsii.getString(1), font));
							cell.setBorder(Rectangle.NO_BORDER);
							tab14.addCell(cell);
						}
						rsii.close();
					}
				}
				rsi.close();
				rsi = stat.executeQuery("SELECT TO_CHAR(NVL("
						+ rs1.getString("PR_IT_TOT_282") + ",0)+NVL("
						+ rs1.getString("PR_IT_DED_SUP_PAY_220")
						+ ",0),'9,99,99,999') FROM DUAL");
				while (rsi.next()) {
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPhrase(new Phrase(rsi.getString(1), font));
					cell.setBorder(Rectangle.NO_BORDER);
					tab14.addCell(cell);
				}
				rsi.close();
				rsi = stat.executeQuery("SELECT TO_CHAR(NVL("
						+ rs1.getString("PR_ITSC_TOT_283") + ",0)+NVL("
						+ rs1.getString("PR_221")
						+ ",0),'9,99,99,999') FROM DUAL");
				while (rsi.next()) {
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPhrase(new Phrase(rsi.getString(1), font));
					cell.setBorder(Rectangle.NO_BORDER);
					tab14.addCell(cell);
				}
				rsi.close();

				if ("2".equals(rs1.getString("PR_PF_CODE_8"))) {
					rsi = stat.executeQuery("SELECT TO_CHAR(NVL("
							+ rs1.getString("PR_GC_TOT_226") + ",0)+NVL("
							+ rs1.getString("PR_279")
							+ ",0),'9,99,99,999') FROM DUAL");
					while (rsi.next()) {
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPhrase(new Phrase(rsi.getString(1), font));
						cell.setBorder(Rectangle.NO_BORDER);
						tab14.addCell(cell);
					}
					rsi.close();
				} else {
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPhrase(new Phrase(" ", font));
					cell.setBorder(Rectangle.NO_BORDER);
					tab14.addCell(cell);
				}
				tab14.completeRow();
				doc.add(tab14);

				Paragraph paragraph5 = new Paragraph();
				// paragraph5.add("----------------------------------------------------------------------------------------------------------------------------------\n");
				doc.add(paragraph5);
				doc.add(new LineSeparator(0.5f, 100, null, 0, -5));
				Paragraph paragraph6 = new Paragraph(
						"ÊîïÒîÕÌî ïÆîïÅî ïÒîÒîÏºî / PF Details.  \n", font);
				paragraph6.setAlignment(Element.ALIGN_LEFT);

				// paragraph6.add("----------------------------------------------------------------------------------------------------------------------------------\n");
				doc.add(paragraph6);
				doc.add(new LineSeparator(0.5f, 100, null, 0, -5));

				float[] widths6 = { 4, 4, 6, 4, 6, 4, 5, 4 };
				PdfPTable tab5 = new PdfPTable(widths6);
				tab5.setWidthPercentage(100);
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase("²îËîî ÓîúÕî / OP.BAL.  ", font));
				cell.setBorder(Rectangle.NO_BORDER);
				tab5.addCell(cell);

				rsi = stat.executeQuery("SELECT TO_CHAR(NVL("
						+ rs1.getString("PR_PF_TOT_BAL_347")
						+ ",0)*100, '9,99,99,999') FROM DUAL");
				while (rsi.next()) {
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(rsi.getString(1), font));
					cell.setBorder(Rectangle.NO_BORDER);
					tab5.addCell(cell);
				}
				rsi.close();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase("¤ëÓî¿îÆî / CONTRIBUTION  ", font));
				cell.setBorder(Rectangle.NO_BORDER);
				tab5.addCell(cell);

				rsi = stat
						.executeQuery("SELECT TO_CHAR("
								+ rs1.getString("PR_274")
								+ ",'9,99,99,999') FROM DUAL");
				while (rsi.next()) {
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(rsi.getString(1), font));
					cell.setBorder(Rectangle.NO_BORDER);
					tab5.addCell(cell);

				}
				rsi.close();
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPhrase(new Phrase("ïÆî´øîÖîó / WITHDRAWAL  ", font));
				cell.setBorder(Rectangle.NO_BORDER);
				tab5.addCell(cell);

				rsi = stat.executeQuery("SELECT TO_CHAR(NVL("
						+ rs1.getString("PR_275")
						+ ",0), '9,99,99,999') FROM DUAL");
				while (rsi.next()) {
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(rsi.getString(1), font));
					cell.setBorder(Rectangle.NO_BORDER);
					tab5.addCell(cell);

				}
				rsi.close();
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPhrase(new Phrase("¤ïÆ»îËî Ïîú´øÏ / CLOSING BAL.  ", font));
				cell.setBorder(Rectangle.NO_BORDER);
				tab5.addCell(cell);

				rsi = stat.executeQuery("SELECT TO_CHAR(NVL("
						+ rs1.getString("PR_PF_TOT_BAL_347") + ",0)*100 + NVL("
						+ rs1.getString("PR_274") + ",0) - NVL("
						+ rs1.getString("PR_275")
						+ ",0), '9,99,99,999') FROM DUAL");
				while (rsi.next()) {
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(rsi.getString(1), font));
					cell.setBorder(Rectangle.NO_BORDER);
					tab5.addCell(cell);

				}
				tab5.completeRow();
				doc.add(tab5);
				rsi.close();
				Paragraph paragraph7 = new Paragraph();
				// paragraph7.add("----------------------------------------------------------------------------------------------------------------------------------\n");
				doc.add(new LineSeparator(0.5f, 100, null, 0, -5));
				paragraph7.add("\n");
				doc.add(paragraph7);
				float[] widths7 = { 5, 4, 8, 5 };
				PdfPTable tab6 = new PdfPTable(widths7);
				tab6.setWidthPercentage(100);
				cell = new PdfPCell();
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPhrase(new Phrase("ÇîûüÆî. Öîë. / PAN NO  ", font));
				cell.setBorder(Rectangle.NO_BORDER);
				tab6.addCell(cell);
				rsi = stat
						.executeQuery("SELECT NVL(PAY_ROLL.PR_PAN_NO_265,0) from ACCT.PAY_ROLL, ACCT.PR_UNIT_CC where PAY_ROLL.PR_YYYY  = "
								+ yyyy
								+ "  AND PAY_ROLL.PR_MM  =  "
								+ mm
								+ "  AND PAY_ROLL.PR_CCTYPE =  "
								+ cctype
								+ "   AND PAY_ROLL.PR_CCNO = "
								+ ccno
								+ "   AND  (PAY_ROLL.PR_PND_CODE_17 = 0  AND PR_UNIT_CC.PUC_UNIT = 'VECC'  AND PR_UNIT_CC.PUC_SUB_UNIT = 'VEC')  AND (PAY_ROLL.PR_CCTYPE = PR_UNIT_CC.PUC_CCTYPE) ORDER BY PAY_ROLL.PR_CCTYPE, PAY_ROLL.PR_CCNO");
				while (rsi.next()) {
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(rsi.getString(1).toUpperCase()));
					cell.setBorder(Rectangle.NO_BORDER);
					tab6.addCell(cell);
				}
				rsi.close();
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPhrase(new Phrase(
						"¤ñ²î»î ¤Òî´øîÓî ÓîúÕî / EARNED LEAVE BALANCE  ", font));
				cell.setBorder(Rectangle.NO_BORDER);
				tab6.addCell(cell);
				rsi = stat
						.executeQuery("SELECT AE_PERID FROM ADMIN.AD_EMP WHERE ADMIN.AD_EMP.AE_CCTYPE = "
								+ cctype
								+ " and ADMIN.AD_EMP.AE_CCNO = "
								+ ccno);
				while (rsi.next()) {
					rsii = stat
							.executeQuery("SELECT AELB_EL_BAL, AELB_EL_PLUS_BAL FROM ADMIN.AD_EMP_LEAVE_BAL WHERE ADMIN.AD_EMP_LEAVE_BAL.AELB_PERID = "
									+ rsi.getString(1));
					while (rsii.next()) {
						rsiii = stat.executeQuery("SELECT NVL("
								+ rsii.getString(1) + ",0)+NVL("
								+ rsii.getString(2) + ",0) FROM DUAL");
						while (rsiii.next()) {
							if ("".equals(rsiii.getString(1))) {
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cell.setPhrase(new Phrase("0".toUpperCase(),
										font));
								cell.setBorder(Rectangle.NO_BORDER);
								tab6.addCell(cell);

							} else {
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cell.setPhrase(new Phrase(rsiii.getString(1),
										font));
								cell.setBorder(Rectangle.NO_BORDER);
								tab6.addCell(cell);

							}
						}
						tab6.completeRow();
						doc.add(tab6);
						rsiii.close();
					}
					rsii.close();
				}
				rsi.close();
				Paragraph paragraph8 = new Paragraph();

				doc.add(new LineSeparator(0.5f, 100, null, 0, -5));
				//paragraph8.add("\n");
				doc.add(paragraph8);
				Paragraph paragraph9 = new Paragraph();
				paragraph9.setAlignment(Element.ALIGN_CENTER);
				paragraph9
						.add("This is a computer generated output and requires no signature.  \n\n\n");
				if (mm == 1) {
					paragraph9.add("HAPPY NEW YEAR  " + yyyy + " \n");
				}
				doc.add(paragraph9);
			}
			rs1.close();

			OutputStream os = response.getOutputStream();
			stat.close();
			conn.close();
			doc.close();
			response.setContentLength(baos.size());
			baos.writeTo(os);
		} catch (Exception ex) {
		}

	}
}
