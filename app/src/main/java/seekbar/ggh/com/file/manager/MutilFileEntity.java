package seekbar.ggh.com.file.manager;

public class MutilFileEntity {
    private String titie;
    private String view;
    private String time;


    public MutilFileEntity(String view) {

        this.view = view;

    }

    public String getTitie() {
        return titie;
    }

    public void setTitie(String titie) {
        this.titie = titie;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}


