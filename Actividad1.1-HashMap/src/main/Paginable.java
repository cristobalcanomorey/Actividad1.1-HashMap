package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Paginable<E extends Producto> {

	/*
	 * add() V remove() V contains() V getPage(int index) V findPageOf(Producto p) V
	 * int getTotalPages() V size() V
	 */

	private ArrayList<Producto<?>> productos = new ArrayList<Producto<?>>();
	private int prodPorPag = 3;
	private int numDePags = 0;

	/***
	 * Crea un paginable transformando un array de productos a un ArrayList sin
	 * productos repetidos
	 * 
	 * @param prod Array de productos
	 */
	Paginable(Producto<?>[] prod) {
		this.productos = quitaRepetidos(prod);
		Double division = (double) productos.size() / prodPorPag;
		numDePags = (int) Math.ceil(division);
	}

	/***
	 * Devuelve un array sin productos repetidos
	 * 
	 * @param prod
	 * @return
	 */
	private ArrayList<Producto<?>> quitaRepetidos(Producto<?>[] prod) {
		ArrayList<Producto<?>> lista = new ArrayList<Producto<?>>(Arrays.asList(prod));
		ArrayList<Producto<?>> listaSinRepe = new ArrayList<Producto<?>>();
		for (Producto<?> producto : lista) {
			if (!listaSinRepe.contains(producto)) {
				listaSinRepe.add(producto);
			}
		}
		return listaSinRepe;
	}

	/***
	 * Añade productos en el array si no está repetida y actualiza el nº de páginas
	 * buscando la página del último producto añadido
	 * 
	 * @param p Producto
	 */
	public boolean add(Producto<?> p) {
		if (!productos.contains(p)) {
			productos.add(p);
			numDePags = findPageOf(p,productos);
			return true;
		} else {
			return false;
		}

	}

	/***
	 * Elimina el producto del array si está y actualiza el nº de páginas buscando
	 * la página del último elemento de la lista
	 * 
	 * @param p Producto
	 */
	public boolean remove(Producto<?> p) {
		if (contains(p)) {
			productos.remove(p);
			numDePags = findPageOf(productos.get(productos.size() - 1),productos);
			return true;
		} else {
			return false;
		}

	}

	/***
	 * Busca si el producto está en el array
	 * 
	 * @param p Producto
	 * @return True si p está en el array
	 */
	public boolean contains(Producto<?> producto) {
		boolean resul = false;
		for (Producto<?> p : productos) {
			if (resul) {
				break;
			} else {
				resul = producto.equals(p);
			}
		}
		return resul;
	}

	/***
	 * Calcula que productos hay en la página pasada por parámetro y los devuelve
	 * como un array
	 * 
	 * @param n Número de página
	 * @return Devuelve array de productos
	 */
	public Producto<?>[] getPage(int n) {
		int tamPag = prodPorPag;
		int primProd = n * prodPorPag;
		int ultProd = primProd + prodPorPag;
		if (ultProd > productos.size()) {
			ultProd = productos.size();
			tamPag = ultProd - primProd;
		}
		Producto<?>[] resul = new Producto<?>[tamPag];
		List pag = productos.subList(primProd, ultProd);

		for (int i = 0; i < pag.size(); i++) {
			resul[i] = (Producto<?>) pag.get(i);
		}
		return resul;
	}

	/***
	 * Devuelve el nº de página en la que se encuentra el producto
	 * 
	 * @param p Producto a buscar
	 * @return -1 si no está o el nº de pag si está
	 */
	public int findPageOf(Producto<?> p, ArrayList<Producto<?>> lista) {
		if (!lista.contains(p)) {
			return -1;
		} else {
			return (int) Math.ceil((double)(lista.indexOf(p) + 1) / prodPorPag);
		}

	}

	public int findPageOf(Producto<?> p, boolean orden) {
		ArrayList<Producto<?>> ordenados = ordenar(orden);
		return findPageOf(p, ordenados);
	}

	private ArrayList<Producto<?>> ordenar(boolean menAMay) {
		ArrayList<Producto<?>> ordenados = productos;
		if (menAMay) {
			Collections.sort(ordenados);
			return ordenados;
		} else {
			Collections.reverse(ordenados);
			return ordenados;
		}
	}
	
	public int getTotalPages() {
		return numDePags;
	}

	public int size() {
		return productos.size();
	}

}
