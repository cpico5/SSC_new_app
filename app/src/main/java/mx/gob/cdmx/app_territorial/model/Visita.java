package mx.gob.cdmx.app_territorial.model;

public class Visita {

    private String pregunta1;
    private String pregunta2;
    private String pregunta3;
    private String pregunta4;
    private String pregunta5;
    private String pregunta6;
    private String pregunta7;
    private String pregunta8;
    private String pregunta9;
    private String pregunta10;
    private String pregunta11;
    private String pregunta12;
    private String pregunta13;
    private String latitud;
    private String longitud;
    private int activo;

    public Visita(String pregunta1, String pregunta2, String pregunta3, String pregunta4, String pregunta5, String pregunta6, String pregunta7, String pregunta8, String pregunta9, String pregunta10, String pregunta11, String pregunta12, String pregunta13, String latitud, String longitud, int activo) {
        this.pregunta1 = pregunta1;
        this.pregunta2 = pregunta2;
        this.pregunta3 = pregunta3;
        this.pregunta4 = pregunta4;
        this.pregunta5 = pregunta5;
        this.pregunta6 = pregunta6;
        this.pregunta7 = pregunta7;
        this.pregunta8 = pregunta8;
        this.pregunta9 = pregunta9;
        this.pregunta10 = pregunta10;
        this.pregunta11 = pregunta11;
        this.pregunta12 = pregunta12;
        this.pregunta13 = pregunta13;
        this.latitud = latitud;
        this.longitud = longitud;
        this.activo = activo;
    }

    public String getPregunta1() {
        return pregunta1;
    }

    public void setPregunta1(String pregunta1) {
        this.pregunta1 = pregunta1;
    }

    public String getPregunta2() {
        return pregunta2;
    }

    public void setPregunta2(String pregunta2) {
        this.pregunta2 = pregunta2;
    }

    public String getPregunta3() {
        return pregunta3;
    }

    public void setPregunta3(String pregunta3) {
        this.pregunta3 = pregunta3;
    }

    public String getPregunta4() {
        return pregunta4;
    }

    public void setPregunta4(String pregunta4) {
        this.pregunta4 = pregunta4;
    }

    public String getPregunta5() {
        return pregunta5;
    }

    public void setPregunta5(String pregunta5) {
        this.pregunta5 = pregunta5;
    }

    public String getPregunta6() {
        return pregunta6;
    }

    public void setPregunta6(String pregunta6) {
        this.pregunta6 = pregunta6;
    }

    public String getPregunta7() {
        return pregunta7;
    }

    public void setPregunta7(String pregunta7) {
        this.pregunta7 = pregunta7;
    }

    public String getPregunta8() {
        return pregunta8;
    }

    public void setPregunta8(String pregunta8) {
        this.pregunta8 = pregunta8;
    }

    public String getPregunta9() {
        return pregunta9;
    }

    public void setPregunta9(String pregunta9) {
        this.pregunta9 = pregunta9;
    }

    public String getPregunta10() {
        return pregunta10;
    }

    public void setPregunta10(String pregunta10) {
        this.pregunta10 = pregunta10;
    }

    public String getPregunta11() {
        return pregunta11;
    }

    public void setPregunta11(String pregunta11) {
        this.pregunta11 = pregunta11;
    }

    public String getPregunta12() {
        return pregunta12;
    }

    public void setPregunta12(String pregunta12) {
        this.pregunta12 = pregunta12;
    }

    public String getPregunta13() {
        return pregunta13;
    }

    public void setPregunta13(String pregunta13) {
        this.pregunta13 = pregunta13;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
}
