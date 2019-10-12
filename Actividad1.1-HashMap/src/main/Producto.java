package main;

public class Producto<E> implements Comparable<Object>{	//implementar comparable

	private Integer id;
	private String nom;
	private Double preu;
	
	Producto(Integer id, String nom, Double preu){
		this.id = id;
		this.nom = nom;
		this.preu = preu;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Double getPreu() {
		return preu;
	}

	public void setPreu(Double preu) {
		this.preu = preu;
	}
	
	/***
	 * Si tienen la misma id devuelve true, si no false. 
	 */
	@Override
	public boolean equals(Object arg0) {
		Producto<?> p = (Producto<?>)arg0;
		return this.id == p.id;
	}
	
	/***
	 * Si son iguales devuelve 0, si el del parámetro es menor 1 si es mayor -1
	 */
	@Override
	public int compareTo(Object arg0) {
		Producto<?> p = (Producto<?>)arg0;
		if(this.id > p.id) {
			return 1;
		} else if(this.id < p.id){
			return -1;
		} else {
			return 0;
		}
	}
	
}
