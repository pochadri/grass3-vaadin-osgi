package org.myftp.gattserver.grass3.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.myftp.gattserver.grass3.model.domain.ContentNode;
import org.myftp.gattserver.grass3.model.domain.ContentTag;
import org.myftp.gattserver.grass3.model.domain.Node;
import org.myftp.gattserver.grass3.model.domain.Quote;
import org.myftp.gattserver.grass3.model.domain.User;
import org.myftp.gattserver.grass3.model.dto.ContentNodeDTO;
import org.myftp.gattserver.grass3.model.dto.ContentTagDTO;
import org.myftp.gattserver.grass3.model.dto.NodeDTO;
import org.myftp.gattserver.grass3.model.dto.QuoteDTO;
import org.myftp.gattserver.grass3.model.dto.UserInfoDTO;

/**
 * <b>Mapper pro různé typy.</b>
 * 
 * <p>
 * Je potřeba aby byl volán na objektech s aktivními proxy objekty. To znamená,
 * že před tímto mapperem nedošlo k uzavření session, ve které byl původní
 * objekt pořízen.
 * </p>
 * 
 * <p>
 * Mapper využívá proxy objekty umístěné v atributech předávaných entit. Během
 * mapování tak může docházet k dotazům na DB, které produkují tyto proxy
 * objekty a které se bez původní session mapovaného objektu neobejdou.
 * </p>
 * 
 * @author gatt
 * 
 */
public enum Mapper {

	INSTANCE;

	/**
	 * Převede {@link User} na {@link UserInfoDTO}
	 * 
	 * @param user
	 * @return
	 */
	public UserInfoDTO map(User user) {
		UserInfoDTO userInfoDTO = new UserInfoDTO();

		userInfoDTO.setConfirmed(user.isConfirmed());
		userInfoDTO.setEmail(user.getEmail());
		userInfoDTO.setId(user.getId());
		userInfoDTO.setLastLoginDate(user.getLastLoginDate());
		userInfoDTO.setName(user.getName());
		userInfoDTO.setPassword(user.getPassword());
		userInfoDTO.setRegistrationDate(user.getRegistrationDate());
		userInfoDTO.setRoles(user.getRoles());

		return userInfoDTO;
	}

	/**
	 * Převede {@link Quote} na {@link QuoteDTO}
	 * 
	 * @param quote
	 * @return
	 */
	public QuoteDTO map(Quote quote) {
		QuoteDTO quoteDTO = new QuoteDTO();

		quoteDTO.setId(quote.getId());
		quoteDTO.setName(quote.getName());

		return quoteDTO;
	}

	/**
	 * Převede {@link ContentNode} na {@link ContentNodeDTO}
	 * 
	 * @param contentNode
	 * @return
	 */
	public ContentNodeDTO map(ContentNode contentNode) {
		ContentNodeDTO contentNodeDTO = new ContentNodeDTO();

		contentNodeDTO.setAuthor(map(contentNode.getAuthor()));
		contentNodeDTO.setContentID(contentNode.getContentId());
		contentNodeDTO.setContentReaderID(contentNode.getContentReaderId());
		contentNodeDTO.setCreationDate(contentNode.getCreationDate());
		contentNodeDTO.setId(contentNode.getId());
		contentNodeDTO.setLastModificationDate(contentNode
				.getLastModificationDate());
		contentNodeDTO.setName(contentNode.getName());
		contentNodeDTO.setParentID(contentNode.getParent().getId());
		contentNodeDTO.setPublicated(contentNode.getPublicated());

		Set<String> tags = new HashSet<String>();
		for (ContentTag contentTag : contentNode.getContentTags()) {
			tags.add(contentTag.getName());
		}
		contentNodeDTO.setContentTags(tags);

		return contentNodeDTO;
	}

	/**
	 * Převede set {@link ContentNode} na list {@link ContentNodeDTO}
	 * 
	 * @param contentNodes
	 * @return
	 */
	public Set<ContentNodeDTO> mapContentNodeCollection(
			Collection<ContentNode> contentNodes) {
		Set<ContentNodeDTO> contentNodeDTOs = new HashSet<ContentNodeDTO>();
		for (ContentNode contentNode : contentNodes) {
			contentNodeDTOs.add(map(contentNode));
		}
		return contentNodeDTOs;
	}

	/**
	 * Převede {@link ContentTag} na {@link ContentTagDTO}
	 * 
	 * @param contentTag
	 * @return
	 */
	public ContentTagDTO map(ContentTag contentTag) {
		ContentTagDTO contentTagDTO = new ContentTagDTO();

		contentTagDTO.setId(contentTag.getId());
		contentTagDTO.setName(contentTag.getName());
		contentTagDTO.setContentNodes(mapContentNodeCollection(contentTag
				.getContentNodes()));

		return contentTagDTO;
	}

	/**
	 * Převede list {@link ContentTag} na list {@link ContentTagDTO}
	 * 
	 * @param contentTags
	 * @return
	 */
	public List<ContentTagDTO> mapContentTagCollection(
			Collection<ContentTag> contentTags) {
		List<ContentTagDTO> contentTagDTOs = new ArrayList<ContentTagDTO>();
		for (ContentTag contentTag : contentTags) {
			contentTagDTOs.add(map(contentTag));
		}
		return contentTagDTOs;
	}

	/**
	 * Převede {@link Node} na {@link NodeDTO}
	 * 
	 * @param node
	 * @return
	 */
	public NodeDTO map(Node node) {
		NodeDTO nodeDTO = new NodeDTO();

		nodeDTO.setId(node.getId());
		nodeDTO.setName(node.getName());
		nodeDTO.setParentID(node.getParent() == null ? null : node.getParent()
				.getId());
		nodeDTO.setContentNodes(mapContentNodeCollection(node.getContentNodes()));

		return nodeDTO;
	}

	/**
	 * Převede list {@link Node} na list {@link NodeDTO}
	 * 
	 * @param nodes
	 * @return
	 */
	public List<NodeDTO> mapNodeCollection(Collection<Node> nodes) {
		List<NodeDTO> nodeDTOs = new ArrayList<NodeDTO>();
		for (Node node : nodes) {
			nodeDTOs.add(map(node));
		}
		return nodeDTOs;
	}
}
