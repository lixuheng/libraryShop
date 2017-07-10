package cn.bewweb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bewweb.beans.Code;
import cn.bewweb.beans.Json;
import cn.bewweb.docments.Category;
import cn.bewweb.entities.Comment;
import cn.bewweb.entities.Goods;
import cn.bewweb.entities.Shop;
import cn.bewweb.entities.User;
import cn.bewweb.service.TouchService_I;
import cn.bewweb.service.TradeService_I;

@Controller
@RequestMapping(value = "/goods")
public class GoodsHandler {
	private static final Logger log = LoggerFactory.getLogger(GoodsHandler.class);
	@Autowired
	private TradeService_I treadService;
	@Autowired
	private TouchService_I touchService;
	
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public String  goSearchGoods(HttpServletRequest request){
		return "goods/searchList";
	}
	
	@RequestMapping(value = "/category",method=RequestMethod.GET)
	public String categroy(String label,Integer page,Integer size,HttpServletRequest request,Model model) {
		page = page == null ? 1:page;
		size = size == null ? 10:size;
		List<Goods> list  = treadService.categoryGoods(label, page, size);
		model.addAttribute("goodsList", list);
		return "goods/categoryList";
	}
	

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public @ResponseBody Json search(String keywords,Integer page,Integer size) {
		page = page == null || page < 1 ? 1 : page;
		size = size == null || size < 1 || size > 100 ? 10 : size;
		log.info(keywords);
		keywords = (null == keywords || "".equals(keywords)) ? "" : keywords.trim();
		if ("".equals(keywords)) {
			return (new Json().setCode(Code.paramTypeError));
		}
		Pattern ps = Pattern.compile("\\s");
		Matcher matcher = ps.matcher(keywords);
		String strs[] = null;
		if (keywords.contains("+")) {
			strs = keywords.split("\\+");
		} else if (matcher.find()) {
			strs = keywords.split("\\s");
		} else if (keywords.contains("&")) {
			strs = keywords.split("&");
		} else if (keywords.contains("|")) {
			strs = keywords.split("|");
		}  else {
			strs = new String[1];
			strs[0] = keywords;
		}
		List<Goods> goodsList = treadService.searchGoods(strs, page, size);
		return new Json().setCode(Code.c1).setList(goodsList);
	}
	

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id,Model model) {
		log.info(""+id);
		Goods goods = treadService.goodsDetail(id);
		model.addAttribute("goods",goods);
		model.addAttribute("book",treadService.getOneBookByPrimaryKey(goods.getEntityId()));
		return "goods/detail";
	}
	
	@RequestMapping(value = "/id/{id}/comment", method = RequestMethod.GET)
	public @ResponseBody Json goodsComment(@PathVariable("id") Long id,Integer no,Integer size) {
		Json json = new Json();
		no = (no==null||no<1||no>1000)? 1 : no;
		size = (size==null||size<1||size>100)?10 :size;
		List<Comment> commentList = touchService.getOneGoodsComment(id, no, size);
		json.setCode(Code.c1);
		json.setList(commentList);
		return json;
	}
	
	@RequestMapping(value = "/id/{id}/shop", method = RequestMethod.GET)
	public @ResponseBody Json goodsShop(@PathVariable("id") Long id) {
		Json json = new Json();
		List<Shop> shopList = new ArrayList<Shop>();
		shopList.add(treadService.getOneShop(id));
		json.setCode(Code.c1);
		json.setList(shopList);
		return json;
	}

}
