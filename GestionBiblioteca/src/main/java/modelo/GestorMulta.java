package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorMulta {

    private File archivo = new File("src/main/resources/data/multas.txt");

    public GestorMulta() {
        try { archivo.createNewFile(); } catch(Exception e){}
    }

    public double calcularMulta(int dias, boolean danado, boolean perdido) {
        double multa = dias * 0.50;
        if (danado) multa += 5.0;
        if (perdido) multa += 20.0;
        return multa;
    }

    public void guardarMulta(Multa m) {
        try(PrintWriter pw = new PrintWriter(new FileWriter(archivo, true))){
            pw.println(m.getCedula() + ";" + m.getDiasRetraso() + ";" +
                    m.isDanado() + ";" + m.isPerdido() + ";" + m.getTotal());
        } catch(Exception e){ e.printStackTrace(); }
    }

    public List<Multa> cargarMultas() {
        List<Multa> lista = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(archivo))){
            String linea;
            while((linea = br.readLine()) != null){
                String[] p = linea.split(";");
                lista.add(new Multa(
                        p[0],
                        Integer.parseInt(p[1]),
                        Boolean.parseBoolean(p[2]),
                        Boolean.parseBoolean(p[3]),
                        Double.parseDouble(p[4])
                ));
            }
        } catch(Exception e){}
        return lista;
    }
}
