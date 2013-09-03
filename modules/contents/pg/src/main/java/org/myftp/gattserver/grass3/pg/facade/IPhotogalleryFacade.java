package org.myftp.gattserver.grass3.pg.facade;

import java.util.Collection;
import java.util.List;

import org.myftp.gattserver.grass3.model.dto.NodeDTO;
import org.myftp.gattserver.grass3.model.dto.UserInfoDTO;
import org.myftp.gattserver.grass3.pg.dto.PhotogalleryDTO;

public interface IPhotogalleryFacade {

	/**
	 * Smaže galerii
	 * 
	 * @param photogallery
	 *            galerie ke smazání
	 * @return {@code true} pokud se zdařilo smazat jinak {@code false}
	 */
	public boolean deletePhotogallery(PhotogalleryDTO photogallery);

	/**
	 * Upraví galerii
	 * 
	 * @param name
	 *            název galerie
	 * @param tags
	 *            klíčová slova galerie
	 * @param publicated
	 *            je galerie publikována ?
	 * @param photogallery
	 *            původní galerie
	 * @return {@code true} pokud se úprava zdařila, jinak {@code false}
	 */
	public boolean modifyPhotogallery(String name, Collection<String> tags,
			boolean publicated, PhotogalleryDTO photogallery, String contextRoot);

	/**
	 * Uloží galerii
	 * 
	 * @param name
	 *            název galerie
	 * @param tags
	 *            klíčová slova galerie
	 * @param publicated
	 *            je galerie publikována ?
	 * @param category
	 *            kategorie do které se vkládá
	 * @param author
	 *            uživatel, který galerii vytvořil
	 * @return identifikátor galerie pokud vše dopadlo v pořádku, jinak
	 *         {@code null}
	 */
	public Long savePhotogallery(String name, Collection<String> tags,
			boolean publicated, NodeDTO category, UserInfoDTO author,
			String contextRoot);

	/**
	 * Získá galerii dle jeho identifikátoru
	 * 
	 * @param id
	 *            identifikátor
	 * @return DTO galerie
	 */
	public PhotogalleryDTO getPhotogalleryForDetail(Long id);

	/**
	 * Získá všechny galerie pro přegenerování
	 */
	public List<PhotogalleryDTO> getAllPhotogalleriesForReprocess();

	/**
	 * Získá všechny galerie pro přehled
	 * 
	 * @return
	 */
	public List<PhotogalleryDTO> getAllPhotogalleriesForOverview();

	/**
	 * Získá všechny galerie a namapuje je pro použití při vyhledávání
	 * 
	 * @return
	 */
	public List<PhotogalleryDTO> getAllPhotogalleriesForSearch();

}
