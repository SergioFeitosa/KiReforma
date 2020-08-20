package br.com.j4busniess.kireforma.campanha;

public class Times {

	public enum TimesEnum {
	    BRAGANTINO("BRAGANTINO"), 
	    CORINTHIANS("CORINTHIANS"), 
	    CRUZEIRO("CRUZEIRO"), 
	    FLAMENGO("FLAMENGO"), 
	    GREMIO("GREMIO"), 
	    MIRASSOL("MIRASSOL"), 
	    PALMEIRAS("PALMEIRAS"),  
	    PONTEPRETA("PONTE PRETA"), 
	    SANTOS("SANTOS"), 
	    SAOPAULO("SÃO PAULO");
		
	    private final String times;
	    
	    TimesEnum(String times){
	        this.times = times;
	    }
	    public String getTimes() {
	        return times;
	    }
	}
	
	
}
