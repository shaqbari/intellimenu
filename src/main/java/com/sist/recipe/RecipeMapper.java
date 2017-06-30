package com.sist.recipe;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.sist.vo.IngrRecipeVO;
import com.sist.vo.IngredientVO;
import com.sist.vo.RecipeVO;
import com.sist.vo.RecipeContentVO;
import com.sist.vo.RecipeTagVO;

public interface RecipeMapper {
	
	// 전체 레시피 수 가져오기
	@Select("SELECT COUNT(*) FROM recipe")
	public int recipeTotal();
	
	//전체 recipe리스트 가져오기
	@Select("SELECT Y.*"
			+ " FROM ("
				+ " SELECT X.*, rownum as num"
				+ " FROM ("
					+ " SELECT id, title, img_ori, img_new, hit"
					+ "	FROM recipe"
					+ " ORDER BY id desc) X) Y"
			+ " WHERE num BETWEEN #{start} and #{end}")
	public List<RecipeVO> recipeList(Map map);
	
	
	
	
	@Insert("Insert into recipe(ID,USER_ID,CAT_SUB_ID) values  ")
	public void insertRecipe(RecipeVO vo);
	
	
	
	/************************** cat_sub_id로 레시피리스트가져오기  ********************************/	
	// sub_cat별 레시피 수 가져오기
	@Select("SELECT COUNT(*) FROM recipe WHERE cat_sub_id=#{cat_sub_id}")
	public int recipeCatSubTotal(int cat_sub_id);
	
	//총페이지수 가져오기
	@Select("SELECT CEIL(COUNT(*)/9) FROM recipe WHERE cat_sub_id=#{cat_sub_id}")
	public int catSubRecipeListTotalPage(int cat_sub_id);
	
	//cat_sub_id로 recipe리스트 가져오기
	@Select("SELECT id, title, img_ori, img_new, hit, num"
			+ " FROM ( SELECT id, title, img_ori, img_new, hit, rownum as num"
			+ " FROM ( SELECT id, title, img_ori, img_new, hit"
			+ "	FROM recipe"
			+ " WHERE cat_sub_id=#{cat_sub_id} ORDER BY id desc))"
			+ " WHERE num BETWEEN #{start} and #{end}")
	public List<RecipeVO> catSubRecipeListData(Map map);
	
	
	
	
	/************************** recipe id로 레시피 상세보기  ********************************/	
	//id로 특정 recipe정보 가져오기
	@Select("SELECT * FROM recipe WHERE id=#{id}")
	public RecipeVO recipeDetail(int id);
	
	//id로 특정 recipe의 content정보(recipe순서) 가져오기
	@Select("Select * FROM recipe_content WHERE recipe_id=#{recipe_id}")
	public List<RecipeContentVO> recipeDetailContent(int recipe_id);
	
	//id로 recipe의 재료정보 가져오기
	@Select("Select ingredient.ID AS id, ingredient.NAME AS name, ingredient.CAL AS cal, ingr_recipe.QUANTITY AS quantity"
			+ " From ingredient, ingr_recipe"
			+ " Where ingredient.id=ingr_recipe.INGREDIENT_ID AND recipe_id=#{recipe_id}")
	public List<IngredientVO> IngrRecipeJoin(int recipe_id);
		
	//id로 recipe의 태그정보 가져오기
	@Select("SELECT * FROM recipe_tag WHERE recipe_id=#{recipe_id}")
	public List<RecipeTagVO> recipeTagSelectListByRecipeId(int recipe_id);
	
	
	
	/*************** 레시피 메인 페이지에 태그이름으로 3개의 추천 레시피리스트가져오기  **********************/	
	@Select(" SELECT id, RECIPE_ID, NAME, hit, rownum AS num"
			+ " From (SELECT * FROM recipe_tag WHERE name=#{name} ORDER BY RECIPE_ID DESC)"
			+ " WHERE rownum BETWEEN 1 AND 3")
	public List<RecipeTagVO> recipeTagSelectList3ByName(String name);
	
	
	
	/************************** 태그이름으로 레시피리스트가져오기  ********************************/	
	@Select("SELECT CEIL(COUNT(*)/9)"
			+ " FROM recipe, recipe_tag"
			+ " WHERE recipe.id=recipe_tag.RECIPE_ID AND recipe_tag.NAME=#{tagName}")
	public int recipeTagListTotalPage(String tagName);
	
	@Select("SELECT id, USER_ID, title, hit, IMG_ORI, IMG_NEW, rownum, num"
			+ " FROM(SELECT id, USER_ID, title, hit, img_new, IMG_ori, rownum AS num FROM"
			+ " (SELECT recipe.id AS id, recipe.USER_ID AS USER_ID, recipe.TITLE AS title, img_new, img_ori, recipe.HIT AS hit"
			+ " FROM recipe, recipe_tag"
			+ " WHERE recipe.id=recipe_tag.RECIPE_ID AND recipe_tag.NAME=#{tagName}"
			+ " ORDER BY recipe.id DESC))"
			+ " WHERE num BETWEEN #{start} AND #{end}")
	public List<RecipeVO> recipeTagListByTagName(Map map);
	
<<<<<<< HEAD
	@Insert("insert into recipe(USER_ID,CAT_SUB_ID,title,summary,reqmember,time,lvl,img_ori,img_new "
			+ "values(recipe_seq.nextval,#{CAT_SUB_ID},#{title},#{summary},#{reqmember},#{time},#{lvl},#{img_ori},#{img_new})")
	
	public void recipeInsert(RecipeVO vo);
	@Select("select RECIPE_SEQ.currval from dual")
	public int recipeCurkey();
	
	
=======

	
	/************************** 재료이름으로 레시피리스트가져오기  ********************************/
	@Select("SELECT COUNT(*)"
			+ " FROM ingredient, INGR_RECIPE, RECIPE"
			+ " WHERE ingredient.ID=ingr_recipe.INGREDIENT_ID AND ingr_recipe.RECIPE_ID=recipe.ID"
			+ " AND ingredient.NAME=#{ingrName}")
	public int recipeIngrListTotal(String ingrName);
	
	@Select("SELECT * "
			+ " FROM (SELECT id, user_id, title, hit, img_ori, img_new, rownum AS num"
			+ " FROM (SELECT recipe.id AS id, recipe.USER_ID AS USER_ID, recipe.TITLE AS title, recipe.HIT AS hit, IMG_ORI, IMG_NEW"
			+ " FROM ingredient, INGR_RECIPE, RECIPE"
			+ " WHERE ingredient.ID=ingr_recipe.INGREDIENT_ID AND ingr_recipe.RECIPE_ID=recipe.ID"
			+ " AND ingredient.NAME like #{ingrName}"
			+ " ORDER BY recipe.ID desc))"
			+ " WHERE num BETWEEN #{start} AND #{end}")
	public List<RecipeVO> recipeIngrListByIngrName(Map map);
	
<<<<<<< HEAD
		
>>>>>>> 7b3ba3af41fc4448c7e55b2ca2e468a21123f90a
=======
	
	
	/*********************************재료이름으로 검색****************************************/
	@Select("SELECT * "
			+ " FROM (SELECT id, user_id, title, hit, img_ori, img_new, rownum AS num"
			+ " FROM (SELECT recipe.id AS id, recipe.USER_ID AS USER_ID, recipe.TITLE AS title, recipe.HIT AS hit, IMG_ORI, IMG_NEW"
			+ " FROM ingredient, INGR_RECIPE, RECIPE"
			+ " WHERE ingredient.ID=ingr_recipe.INGREDIENT_ID AND ingr_recipe.RECIPE_ID=recipe.ID"
			+ " AND ingredient.NAME like '%'||#{searchKeyword}||'%'"
			+ " ORDER BY recipe.ID desc))"
			+ " WHERE num BETWEEN #{start} AND #{end}")
	public List<RecipeVO> searchRecipeIngrListByIngrName(Map map);
	
	/*********************************레시피 제목으로 검색****************************************/
	@Select("SELECT COUNT(*) FROM RECIPE WHERE title LIKE '%'#{searchKeyword}'%'")
	public int searchRecipeListTotal(String searchKeyword);
	
	@Select(" SELECT *"
			+ " From(SELECT id, USER_ID, CAT_SUB_ID, hit, TITLE, IMG_ORI, IMG_NEW, rownum AS num"
			+ " FROM(SELECT * FROM RECIPE"
			+ " WHERE title LIKE '%'${searchKeyword}'%'"
			+ " ORDER BY ID DESC))"
			+ " WHERE num BETWEEN #{start} AND #{end}")
	public int searchRecipeListByRecipeTitle(Map map);

	
	/******************************     태그이름으로 검색      ************************************/
	@Select("SELECT *"
			+ " FROM (SELECT id, USER_ID, title, hit, img_new, IMG_ori, rownum AS num"
			+ " FROM (SELECT recipe.id AS id, recipe.USER_ID AS USER_ID, recipe.TITLE AS title, img_new, img_ori, recipe.HIT AS hit"
			+ " FROM recipe, recipe_tag"
			+ " WHERE recipe.id=recipe_tag.RECIPE_ID AND recipe_tag.NAME like '%'||#{searchKeyword}||'%'"
			+ " ORDER BY recipe.id DESC))"
			+ " WHERE num BETWEEN #{start} AND #{end}")
	public List<RecipeVO> searchRecipeTagListByTagName(Map map);
	
>>>>>>> 1e37a56cba34e2ca0ea14b0a66f3e0e3577100ec
}
