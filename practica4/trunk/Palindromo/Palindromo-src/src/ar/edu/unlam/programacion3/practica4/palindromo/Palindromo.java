package ar.edu.unlam.programacion3.practica4.palindromo;

import java.io.*;

public class Palindromo {
	
	private static final String NEWLINE = System.getProperty("line.separator");
	
	public static void main(String[] args) {
		// LECTURA DEL ARCHIVO
		
		// PARA LECTURA
		String wordToProcess;		
		File textFileIn = null;
		FileReader textFileReader = null;
		BufferedReader textBufferedReader = null;
		
		// PARA ESCRITURA
		String strResult = "";
		File textFileOut = null;
		PrintWriter textFileWriter = null; 
		
		try {
			// ABRO EL ARCHIVO.			 
			textFileIn = new File("palin.in");
			textFileReader = new FileReader(textFileIn);
			textBufferedReader = new BufferedReader(textFileReader);
			
			// LEO
			wordToProcess = textBufferedReader.readLine();
			
			// PROCESO Y RECIBO EL RESULTADO UN UN STRING.
			strResult = processWord2(wordToProcess);
			
			// ABRO ARCHVO .OUT
			textFileOut = new File("palin.out");
			textFileWriter = new PrintWriter(textFileOut);
			
			// ESCRIBO EL RESULTADO
			textFileWriter.print(strResult);
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex){
			ex.printStackTrace();
		} 
		finally {
			try {
				if (textFileReader != null) {
					textFileReader.close();
				}
				
				if (textFileWriter != null) {
					textFileWriter.close();
				}

			} catch (IOException ex) {
				ex.printStackTrace();
			}						
		}
	}
	
	public static boolean esPalindromo(String aWord) {
		boolean result = false;
		String auxReversedWord = new StringBuffer(aWord).reverse().toString();
		if((aWord.length() > 1) && aWord.compareToIgnoreCase(auxReversedWord) == 0) {
			result = true;
		}
		return result;
	}
	
	public static boolean esIPalindromo(String aWord) {
		boolean result = false;
		
		if(esPalindromo(aWord.substring(1, aWord.length()))){
			result = true;
		}
		return result;
	} 
	
	public static boolean esDPalindromo(String aWord) {
		boolean result = false;
		
		if(esPalindromo(aWord.substring(0, aWord.length() - 1))){
			result = true;
		}
		return result;
	}

	
	public static String processWord2(String aWord) {
		String result  = "";
		String subStr1 = "";
		String subStr2 = "";
		String types1  = "";
		String types2  = "";
		
		for (int i = 2; i < aWord.length()-1; i++) {
			// SEPARO EN DOS PALABRAS
			subStr1 = aWord.substring(0, i);
			subStr2 = aWord.substring(i, aWord.length());
			
			// ANALIZO CADA SUB CADENA.
			types1 = getWordTypes(subStr1);
			types2 = getWordTypes(subStr2);
			
			// SI AMBAS SON DE ALGUN TIPO, AGREGO AL RESULTADO.
			if (!types1.isEmpty() && !types2.isEmpty()) {
				result += subStr1 + types1 + NEWLINE;
				result += subStr2 + types2 + NEWLINE;
			}

			// LIMPIO LOS TIPOS.
			types1 = "";
			types2 = "";
		}
			
		return result;
	}
	
	private static String getWordTypes(String aWord) {
		String types = "";
		
		if(esPalindromo(aWord)) {
			types += " palindromo";
		} 
		if (esIPalindromo(aWord)){
			types += " i-palindromo";
		} 
		if (esDPalindromo(aWord)) {
			types += " d-palindromo";
		}
		
		return types;
	}
	
	// Se comenta este metodo ya que evalua mas posiblidades que las pedidas
	// en el enunciado del ejercicio. aunque funciona correctamente.
	/*
	public static String processWord(String aWord) {
		String subStr = "";
		String types = "";
		String result = "";
		int subLength = 2;
		
		
		while (subLength <= aWord.length()) {
			
			for (int i = 0; i <= (aWord.length() - subLength); i++) {
				// EVALUO TODOS LOS SEGMENTOS DE 2 , 3,.., N LETRAS.
				subStr = String.copyValueOf(aWord.toCharArray(), i, subLength);

				if(esPalindromo(subStr)) {
					types = types + " palindromo";
				} 
				if (esIPalindromo(subStr)){
					types = types + " i-palindromo";
				} 
				if (esDPalindromo(subStr)) {
					types = types + " d-palindromo";
				}
				
				// SI ES DE ALGUN TIPO, AGREGO Y LIMPIO LOS TIPOS.
				if (!types.isEmpty()) {
					result = result + subStr +	types + NEWLINE;
					types = "";
				}			
			}
			
			subLength++;
		}
		
		if (result.isEmpty()) {
			result = "No se puede.";
		}
		
		return result;
	}
	*/
}
