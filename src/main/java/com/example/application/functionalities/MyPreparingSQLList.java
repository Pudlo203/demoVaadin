package com.example.application.functionalities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class MyPreparingSQLList {

	Calendar calendar = Calendar.getInstance();
	String year = String.valueOf(calendar.get(Calendar.YEAR));
	List<String> list = new ArrayList<>();
	final String INSERT_BEGIN = "INSERT INTO rwa(symbol, nadrzedne, haslo, arch_km, arch_ko, arch_kd, arch_ki, uwagi, podteczka, rok_rwa, wersja,  data_utworzenia, utworzony_przez, data_modyfikacji, zmieniony_przez) VALUES ";
	final String ARCH_KO = "NULL";
	final String ARCH_KD = "NULL";
	final String ARCH_KI = "NULL";
	final String PODTECZKA = "N";
	final String DATA_UTWORZENIA = "current_timestamp::timestamp(0)";
	final String UTWORZONY_PRZEZ = "1";
	final String DATA_MODYFIKACJI = "NULL";
	final String ZMIENIONY_PRZEZ = "NULL";
	final String CURRENT_YEAR = year;
	final String LAST_RWA_VERSION = "(select max(id_wr) from wersje_rwa)";

	public List<String> getSQLStrings(List<String> preparedStrings, String separator) {

		for (String string : preparedStrings) {

			String[] values = string.split(separator);

			String symbol = "";
			String nadrzedne = "NULL";

			for (int i = 0; i < 4; i++) {
				if (!values[i].equals(null) && !Objects.equals(values[i], "") && !Objects.equals(values[i], " ") && !Objects.equals(values[i], "  ")) {
					values[i] = values[i].replaceAll("[^\\x00-\\x7F]", "");
					values[i] = values[i].replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
					values[i] = values[i].replaceAll("\\p{C}", "");
					symbol = values[i];
				}
			}
			if (symbol.contains("["))
				symbol = symbol.substring(1);

			if (symbol.equals("\uFEFF0"))
				symbol = "0";

			if (symbol.length() > 1 && symbol.substring(symbol.length() - 1).equalsIgnoreCase(" "))
				symbol = symbol.substring(0, symbol.length() - 1);

			if (symbol.equals("0")) {
				nadrzedne = "NULL";
			} else if (symbol.length() > 1) {
				nadrzedne = "'" + symbol.subSequence(0, symbol.length() - 1) + "'";
			} else {
				nadrzedne = "NULL";
			}

			String haslo = values[4];
			if (haslo.charAt(0) == ' ')
				haslo = haslo.substring(1);

			if (haslo.charAt(haslo.length() - 1) == ' ')
				haslo = haslo.substring(0, haslo.length() - 1);

			String arch_kms = values[5];
			arch_kms.replaceAll("[^\\x00-\\x7F]", "");
			arch_kms.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
			arch_kms.replaceAll("\\p{C}", "");
			String arch_km = "'" + arch_kms.replaceAll(" ", "") + "'";
			if (arch_km.equals("''")) {
				arch_km = "NULL";
			}

			String uwagi = values[6];
			if (values.length > 7) {
				for (int i = 7; i < values.length; i++) {
					uwagi = uwagi + separator + values[i];
				}
			}

			if (uwagi.contains("]\n"))
				uwagi = (String) uwagi.subSequence(0, uwagi.length() - 2);

			if (uwagi.equalsIgnoreCase("''"))
				uwagi.replace("''", "NULL");

			StringBuilder query = new StringBuilder();
			query.append(INSERT_BEGIN);
			query.append("( ");
			query.append("'" + symbol + "', ");
			query.append("" + nadrzedne + ", ");
			query.append("'" + haslo + "', ");
			query.append("" + arch_km + ", ");
			query.append("" + ARCH_KO + ", ");
			query.append("" + ARCH_KD + ", ");
			query.append("" + ARCH_KI + ", ");
			query.append("'" + uwagi + "', ");
			query.append("'" + PODTECZKA + "', ");
			query.append("" + CURRENT_YEAR + ", ");
			query.append("" + LAST_RWA_VERSION + ", ");
			query.append("" + DATA_UTWORZENIA + ", ");
			query.append("" + UTWORZONY_PRZEZ + ", ");
			query.append("" + DATA_MODYFIKACJI + ", ");
			query.append("" + ZMIENIONY_PRZEZ + "");
			query.append(" );");
			list.add(query.toString());
		}
		return list;
	}
}
