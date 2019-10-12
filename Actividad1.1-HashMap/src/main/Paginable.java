package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Paginable<E extends Producto> {

	/*
	 * add() V remove() V contains() V getPage(int index) V findPageOf(Producto p) V
	 * int getTotalPages() V size() V
	 */

//	private ArrayList<Producto<?>> productos = new ArrayList<Producto<?>>();
	private HashMap<Integer, Producto<?>> productos = new HashMap<Integer, Producto<?>>();
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
	private HashMap<Integer, Producto<?>> quitaRepetidos(Producto<?>[] prod) {
		ArrayList<Producto<?>> lista = new ArrayList<Producto<?>>(Arrays.asList(prod));
		ArrayList<Producto<?>> listaSinRepe = new ArrayList<Producto<?>>();
		for (Producto<?> producto : lista) {
			if (!listaSinRepe.contains(producto)) {
				listaSinRepe.add(producto);
			}
		}
		return deArrayAHashMap(listaSinRepe);
	}
	
	private HashMap<Integer, Producto<?>> deArrayAHashMap(ArrayList<Producto<?>> prod){
		HashMap<Integer, Producto<?>> mProductos = new HashMap<>(prod.size());
		for (Producto<?> producto : prod) {
			mProductos.put(producto.getId(), producto);
		}
		return mProductos;
	}

	/***
	 * Añade productos en el array si no está repetida y actualiza el nº de páginas
	 * buscando la página del último producto añadido
	 * 
	 * @param p Producto
	 */
	public boolean add(Producto<?> p) {
		if (!productos.containsKey(p.getId())) {
			productos.put(p.getId(),p);
			numDePags = findPageOf(p, deHashAArray(productos));
			return true;
		} else {
			return false;
		}

	}

	private ArrayList<Producto<?>> deHashAArray(HashMap<Integer, Producto<?>> hash) {
		Set<Integer> ids = productos.keySet();
		Object[] array = ids.toArray();
		ArrayList<Producto<?>> arrayList = new ArrayList<Producto<?>>();
		for (Object id : array) {
			arrayList.add(hash.get(id));
		}
		return arrayList;		
		
	}

	/***
	 * Elimina el producto del array si está y actualiza el nº de páginas buscando
	 * la página del último elemento de la lista
	 * 
	 * @param p Producto
	 */
	public boolean remove(Producto<?> p) {
		if (contains(p)) {
			productos.remove(p.getId());
			ArrayList<Producto<?>> lista = deHashAArray(productos);
			numDePags = findPageOf(lista.get(lista.size() - 1),lista);
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
		Set<Integer> ids = productos.keySet();
		for(Integer id: ids) {
			if(resul) {
				break;
			} else {
				resul = producto.equals(productos.get(id));
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
		ArrayList<Producto<?>> lista = deHashAArray(productos);
		List pag = lista.subList(primProd, ultProd);

		for (int i = 0; i < pag.size(); i++) {
			resul[i] = (Producto<?>) pag.get(i);
		}
		return resul;
	}
	
	/***
	 * 
	 * @param p
	 * @return
	 */
	public int findPageOf(Producto<?>p) {
		return findPageOf(p,deHashAArray(productos));
	}
	
	/***
	 * Devuelve el nº de página en la que se encuentra el producto
	 * 
	 * @param p Producto a buscar
	 * @return -1 si no está o el nº de pag si está
	 */
	private int findPageOf(Producto<?> p, ArrayList<Producto<?>> lista) {
		if (!lista.contains(p)) {
			return -1;
		} else {
			return (int) Math.ceil((double)(lista.indexOf(p) + 1) / prodPorPag);
		}
	}
	
	/***
	 * 
	 * @param p
	 * @param orden
	 * @return
	 */
	public int findPageOf(Producto<?> p, boolean orden) {
		ArrayList<Producto<?>> ordenados = ordenar(orden);
		return findPageOf(p, ordenados);
	}
	
	/***
	 * 
	 * @param menAMay
	 * @return
	 */
	private ArrayList<Producto<?>> ordenar(boolean menAMay) {
		ArrayList<Producto<?>> ordenados = deHashAArray(productos);
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
