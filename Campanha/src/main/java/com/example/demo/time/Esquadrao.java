package com.example.demo.time;

public enum Esquadrao {
	
	    BRAGANTINO("BRAGANTINO"), 
	    CORINTHIANS("CORINTHIANS"), 
	    CRUZEIRO("CRUZEIRO"), 
	    FLAMENGO("FLAMENGO"), 
	    GREMIO("GREMIO"), 
	    MIRASSOL("MIRASSOL"), 
	    PALMEIRAS("PALMEIRAS"),  
	    PONTEPRETA("PONTEPRETA"), 
	    SANTOS("SANTOS"), 
	    SAOPAULO("SÃO PAULO");
		
	    private final String name;
	    
	    Esquadrao(String name){
	        this.name = name;
	    }
	    public String getName() {
	        return name;
	    }
	}
	
