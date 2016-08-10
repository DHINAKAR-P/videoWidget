package com.npb.gp.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.npb.gp.constants.GpUiTypeConstants;
import com.npb.gp.dao.mysql.support.screen.GpDto_screen_and_widgets;
import com.npb.gp.dao.mysql.support.screen.InsertScreen;
import com.npb.gp.dao.mysql.support.screen.InsertScreenWidget;
import com.npb.gp.dao.mysql.support.screen.ScreenBaseMapper;
import com.npb.gp.dao.mysql.support.screen.Screen_with_widget_mapper;
import com.npb.gp.dao.mysql.support.screen.UpdateScreen;
import com.npb.gp.dao.mysql.support.screen.UpdateScreenWidget;
import com.npb.gp.dao.mysql.support.screen.WidgetBaseMapper;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUiWidgetX;
import com.npb.gp.interfaces.dao.IGpScreenXDao;

/**
 *
 * @author Suresh Palanisamy</br> Date Created: 07/04/2015</br>
 * @since .75</p>
 *
 *
 *        The purpose of this class is to interact with the db for the basic
 *        search</br> and CRUD operations for a screen</p>
 *
 */

@Repository("GpScreenXDao")
public class GpScreenXDao implements IGpScreenXDao {

	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private InsertScreen insert_screen;
	private InsertScreenWidget insert_widget;

	private UpdateScreen update_screen;
	private UpdateScreenWidget update_widget;

	@Value("${insert_screen.sql}")
	private String insertScreen;

	@Value("${insert_widget.sql}")
	private String insertWidget;

	@Value("${update_screen.sql}")
	private String updateScreen;

	@Value("${update_widget.sql}")
	private String updateWidget;

	@Value("${delete_screen.sql}")
	private String deleteScreen;

	@Value("${delete_widget.sql}")
	private String deleteWidget;

	@Value("${find_all_widgets_by_screen.sql}")
	private String findAllWidgetsByScreen;

	@Value("${get_base_by_activity_id.sql}")
	private String get_base_by_activity_id_sql;

	@Value("${find_by_id.sql}")
	private String find_by_id;

	@Value("${find_all_base_by_projectid.sql}")
	private String find_all_base_by_projectid;

	@Value("${find_by_project_id.sql}")
	private String find_by_project_id;

	@Value("${get_by_activity_id.sql}")
	private String get_by_activity_id_sql;
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	@Override
	public GpScreenX insert(GpScreenX ascreen) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", ascreen.getName());
		paramMap.put("description", ascreen.getDescription());
		paramMap.put("label", ascreen.getLabel());
		paramMap.put("type", ascreen.getType());
		paramMap.put("notes", ascreen.getNotes());
		paramMap.put("height", ascreen.getHeight());
		paramMap.put("width", ascreen.getWidth());
		paramMap.put("client_device_type_id",
				ascreen.getClient_device_type_id());
		paramMap.put("client_device_type", ascreen.getClient_device_type());
		paramMap.put("client_device_type_name",
				ascreen.getClient_device_type_name());
		paramMap.put("client_device_type_label",
				ascreen.getClient_device_type_label());
		paramMap.put("client_device_type_description",
				ascreen.getClient_device_type_description());
		paramMap.put("client_device_screen_size",
				ascreen.getClient_device_screen_size());
		paramMap.put("client_device_resolution",
				ascreen.getClient_device_resolution());
		paramMap.put("client_device_ppcm", ascreen.getClient_device_ppcm());
		paramMap.put("client_device_type_os_name",
				ascreen.getClient_device_type_os_name());
		paramMap.put("client_device_type_os_version",
				ascreen.getClient_device_type_os_version());
		paramMap.put("landscape_image_name", ascreen.getLandscape_image_name());
		paramMap.put("portrait_image_name", ascreen.getPortrait_image_name());
		paramMap.put("current_orientation", ascreen.getOrientation());
		paramMap.put("is_orientation_locked", ascreen.getOrientation_locked());
		paramMap.put("activityid", ascreen.getActivity_id());
		paramMap.put("projectid", ascreen.getProjectid());
		paramMap.put("created_date", new Date());
		paramMap.put("created_by", ascreen.getCreatedby());
		paramMap.put("last_modified_date", new Date());
		paramMap.put("last_modified_by", ascreen.getLastmodifiedby());
		paramMap.put("primary_noun_id", ascreen.getPrimary_noun_id());
		paramMap.put("client_language_type", ascreen.getClient_language_type());
		paramMap.put("client_library_type", ascreen.getClient_library_type());

		// String sec_noun_str =
		// GpGenericRecordParserBuilder.buildDelimitedString(ascreen.getSecondary_noun_ids());
		// paramMap.put("secondary_noun_ids", sec_noun_str);

		/* Temp */
		paramMap.put("secondary_noun_ids", ";");

		paramMap.put("human_language_id", ascreen.getHuman_language_id());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertScreen.SQL_INSERT_SCREEN = insertScreen;
		this.insert_screen = new InsertScreen(this.dataSource);
		this.insert_screen.updateByNamedParam(paramMap, keyHolder);
		ascreen.setId(keyHolder.getKey().longValue());
		return ascreen;
	}

	@Override
	public GpScreenX update(GpScreenX ascreen) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", ascreen.getId());
		paramMap.put("name", ascreen.getName());
		paramMap.put("description", ascreen.getDescription());
		paramMap.put("label", ascreen.getLabel());
		paramMap.put("type", ascreen.getType());
		paramMap.put("notes", ascreen.getNotes());
		paramMap.put("height", ascreen.getHeight());
		paramMap.put("width", ascreen.getWidth());
		paramMap.put("client_device_type_id",
				ascreen.getClient_device_type_id());
		paramMap.put("client_device_type", ascreen.getClient_device_type());
		paramMap.put("client_device_type_name",
				ascreen.getClient_device_type_name());
		paramMap.put("client_device_type_label",
				ascreen.getClient_device_type_label());
		paramMap.put("client_device_type_description",
				ascreen.getClient_device_type_description());
		paramMap.put("client_device_screen_size",
				ascreen.getClient_device_screen_size());
		paramMap.put("client_device_resolution",
				ascreen.getClient_device_resolution());
		paramMap.put("client_device_ppcm", ascreen.getClient_device_ppcm());
		paramMap.put("client_device_type_os_name",
				ascreen.getClient_device_type_os_name());
		paramMap.put("client_device_type_os_version",
				ascreen.getClient_device_type_os_version());
		paramMap.put("landscape_image_name", ascreen.getLandscape_image_name());
		paramMap.put("portrait_image_name", ascreen.getPortrait_image_name());
		paramMap.put("current_orientation", ascreen.getOrientation());
		paramMap.put("is_orientation_locked", ascreen.getOrientation_locked());
		paramMap.put("activityid", ascreen.getActivity_id());
		paramMap.put("projectid", ascreen.getProjectid());
		paramMap.put("created_date", new Date());
		paramMap.put("created_by", ascreen.getCreatedby());
		paramMap.put("last_modified_date", new Date());
		paramMap.put("last_modified_by", ascreen.getLastmodifiedby());
		paramMap.put("primary_noun_id", ascreen.getPrimary_noun_id());
		paramMap.put("client_language_type", ascreen.getClient_language_type());
		paramMap.put("client_library_type", ascreen.getClient_library_type());

		// String sec_noun_str =
		// GpGenericRecordParserBuilder.buildDelimitedString(ascreen.getSecondary_noun_ids());
		// paramMap.put("secondary_noun_ids", sec_noun_str);

		/* Temp */
		paramMap.put("secondary_noun_ids", ";");

		paramMap.put("human_language_id", ascreen.getHuman_language_id());

		UpdateScreen.SQL_UPDATE_SCREEN = updateScreen;
		this.update_screen = new UpdateScreen(this.dataSource);
		this.update_screen.updateByNamedParam(paramMap);
		return ascreen;
	}

	@Override
	public void delete(long id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);

		this.namedParameterJdbcTemplate.execute(deleteScreen, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
	}

	@Override
	public GpUiWidgetX insert_a_widget(GpUiWidgetX awidget, long screen_id,
			long user_id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", awidget.getName());
		paramMap.put("label", awidget.getLabel());
		paramMap.put("description", awidget.getDescription());
		paramMap.put("notes", awidget.getNotes());
		paramMap.put("parentid", awidget.getParentid());
		// paramMap.put("parent", awidget.getParent());
		paramMap.put("parent_name", awidget.getParent_name());
		paramMap.put("screen_id", awidget.getScreen_id());
		paramMap.put("number_of_children", awidget.getNumber_of_children());
		paramMap.put("type", awidget.getType());
		paramMap.put("supports_label", awidget.getSupports_label());
		paramMap.put("is_container", awidget.getIs_container());
		paramMap.put("ui_technology", awidget.getUi_technology());
		paramMap.put("data_binding_context", awidget.getData_binding_context());
		paramMap.put("verb_binding_context", awidget.getVerb_binding_context());
		paramMap.put("noun_id", awidget.getNoun_id());
		paramMap.put("noun_attribute_id", awidget.getNoun_attribute_id());
		paramMap.put("extended_attributes", awidget.getExtended_attributes());
		paramMap.put("events", awidget.getEvents());
		paramMap.put("event_verb_combo", awidget.getEvent_verb_combo());
		paramMap.put("verb_target", awidget.getVerb_target());
		paramMap.put("width", awidget.getWidth());
		paramMap.put("height", awidget.getHeight());
		// paramMap.put("font_size", awidget.getFontSize());
		paramMap.put("x", awidget.getX());
		paramMap.put("y", awidget.getY());
		paramMap.put("portrait_x", awidget.getPortraitX());
		paramMap.put("portrait_y", awidget.getPortraitY());
		paramMap.put("portrait_width", new Long(awidget.getHeight()).toString());
		paramMap.put("portrait_height",
				new Long(awidget.getHeight()).toString());
		paramMap.put("landscape_x", awidget.getLandscapeX());
		paramMap.put("landscape_y", awidget.getLandscapeY());
		paramMap.put("landscape_width",
				new Long(awidget.getLandscapeX()).toString());
		paramMap.put("landscape_height",
				new Long(awidget.getLandscapeY()).toString());
		paramMap.put("created_date", new Date());
		paramMap.put("created_by", user_id);
		paramMap.put("last_modified_date", new Date());
		paramMap.put("last_modified_by", user_id);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		InsertScreenWidget.SQL_INSERT_WIDGET = insertWidget;
		this.insert_widget = new InsertScreenWidget(this.dataSource);
		this.insert_widget.updateByNamedParam(paramMap, keyHolder);
		awidget.setId(keyHolder.getKey().longValue());
		return awidget;
	}

	@Override
	public GpUiWidgetX update_a_widget(GpUiWidgetX awidget, long screen_id,
			long user_id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", awidget.getId());
		paramMap.put("name", awidget.getName());
		paramMap.put("label", awidget.getLabel());
		paramMap.put("description", awidget.getDescription());
		paramMap.put("notes", awidget.getNotes());
		paramMap.put("parentid", awidget.getParentid());
		// paramMap.put("parent", awidget.getParent());
		paramMap.put("parent_name", awidget.getParent_name());
		paramMap.put("screen_id", awidget.getScreen_id());
		paramMap.put("number_of_children", awidget.getNumber_of_children());
		paramMap.put("type", awidget.getType());
		paramMap.put("supports_label", awidget.getSupports_label());
		paramMap.put("is_container", awidget.getIs_container());
		paramMap.put("ui_technology", awidget.getUi_technology());
		paramMap.put("data_binding_context", awidget.getData_binding_context());
		paramMap.put("verb_binding_context", awidget.getVerb_binding_context());
		paramMap.put("noun_id", awidget.getNoun_id());
		paramMap.put("noun_attribute_id", awidget.getNoun_attribute_id());
		paramMap.put("extended_attributes", awidget.getExtended_attributes());
		paramMap.put("events", awidget.getEvents());
		paramMap.put("event_verb_combo", awidget.getEvent_verb_combo());
		paramMap.put("verb_target", awidget.getVerb_target());
		paramMap.put("width", awidget.getWidth());
		paramMap.put("height", awidget.getHeight());
		// paramMap.put("font_size", awidget.getFontSize());
		paramMap.put("x", awidget.getX());
		paramMap.put("y", awidget.getY());
		paramMap.put("portrait_x", awidget.getPortraitX());
		paramMap.put("portrait_y", awidget.getPortraitY());
		paramMap.put("portrait_width", new Long(awidget.getHeight()).toString());
		paramMap.put("portrait_height",
				new Long(awidget.getHeight()).toString());
		paramMap.put("landscape_x", awidget.getLandscapeX());
		paramMap.put("landscape_y", awidget.getLandscapeY());
		paramMap.put("landscape_width",
				new Long(awidget.getLandscapeX()).toString());
		paramMap.put("landscape_height",
				new Long(awidget.getLandscapeY()).toString());
		paramMap.put("created_date", new Date());
		paramMap.put("created_by", user_id);
		paramMap.put("last_modified_date", new Date());
		paramMap.put("last_modified_by", user_id);

		UpdateScreenWidget.SQL_UPDATE_WIDGET = updateWidget;
		this.update_widget = new UpdateScreenWidget(this.dataSource);
		this.update_widget.updateByNamedParam(paramMap);

		return awidget;
	}

	@Override
	public void delete_a_widget(long id, long screen_id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("screen_id", screen_id);

		this.namedParameterJdbcTemplate.execute(deleteWidget, paramMap,
				new PreparedStatementCallback<Object>() {
					@Override
					public Object doInPreparedStatement(PreparedStatement ps)
							throws SQLException, DataAccessException {
						return ps.executeUpdate();
					}
				});
	}

	@Override
	public Map<String, GpUiWidgetX> find_all_widgets_by_screen(long screen_id) {
		Integer key = 0;
		WidgetBaseMapper widget_base_mapper = new WidgetBaseMapper();
		Map<String, GpUiWidgetX> the_widgets = new HashMap<String, GpUiWidgetX>();

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("screen_id", screen_id);

		List<GpUiWidgetX> widgets_list = this.namedParameterJdbcTemplate.query(
				findAllWidgetsByScreen, parameters, widget_base_mapper);

		if (widgets_list.size() > 0) {
			for (GpUiWidgetX gpUiWidgetX : widgets_list) {
				the_widgets.put((++key).toString(), gpUiWidgetX);
			}
		}

		return the_widgets;
	}

	@Override
	public GpScreenX find_by_id(long screen_id) throws Exception {

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", screen_id);

		Screen_with_widget_mapper screen_and_widget_mapper = new Screen_with_widget_mapper();
		List<GpDto_screen_and_widgets> dto_list = this.namedParameterJdbcTemplate
				.query(find_by_id, parameters, screen_and_widget_mapper);

		if (dto_list.size() < 1) {
			throw new Exception("screenid number " + screen_id
					+ " was not found");
		}

		return this.build_screen(dto_list);
	}

	@Override
	public ArrayList<GpScreenX> find_all_base_by_activity_id(long activity_id)
			throws Exception {

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("activity_id", activity_id);

		ScreenBaseMapper screen_mapper = new ScreenBaseMapper();

		List<GpScreenX> screen_list = this.namedParameterJdbcTemplate.query(
				this.get_base_by_activity_id_sql, parameters, screen_mapper);

		/*
		 * if (screen_list.size() < 1) { throw new
		 * Exception("no screens found for activity_id : " + activity_id); }
		 */

		return (ArrayList<GpScreenX>) screen_list;
	}
	
	public List<Long> find_all_by_activity_id(long activity_id)
			throws Exception {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("activity_id", activity_id);
		List<Long> screens = this.namedParameterJdbcTemplate.queryForList(this.get_by_activity_id_sql, parameters, Long.class);
		return screens;
	}

	@Override
	public ArrayList<GpScreenX> find_by_project_id(long project_id)
			throws Exception {

		MapSqlParameterSource parameters;
		//System.out.println("Project id " + project_id);
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", project_id);

		ScreenBaseMapper screen_mapper = new ScreenBaseMapper();
		List<GpScreenX> screen_list = this.namedParameterJdbcTemplate.query(
				this.find_by_project_id, parameters, screen_mapper);

		/*
		 * if (screen_list.size() < 1) { throw new
		 * Exception("no screens where found for project id:  " + project_id,
		 * new Throwable("99")); }
		 */

		return (ArrayList<GpScreenX>) screen_list;
	}

	@Override
	public List<GpScreenX> find_all_base_by_projectid(long projectid)
			throws Exception {

		MapSqlParameterSource parameters;

		parameters = new MapSqlParameterSource();
		parameters.addValue("projectid", projectid);

		ScreenBaseMapper screen_base_mapper = new ScreenBaseMapper();
		List<GpScreenX> screen_list = this.namedParameterJdbcTemplate
				.query(this.find_all_base_by_projectid, parameters,
						screen_base_mapper);

		if (screen_list.size() < 1) {
			return new ArrayList<GpScreenX>();
		}

		return screen_list;
	}

	@Override
	public ArrayList<GpScreenX> find_by_activity_id(long activity_id)
			throws Exception {
		return null;
	}

	private GpScreenX build_screen(List<GpDto_screen_and_widgets> dto_list) {

		GpScreenX the_screen = new GpScreenX();
		GpDto_screen_and_widgets a_dto = dto_list.get(0);
		the_screen.setId(a_dto.getScreen_id());
		the_screen.setName(a_dto.getScreen_name());
		the_screen.setLandscape_image_name(a_dto
				.getScreen_landscape_image_name());
		the_screen
				.setPortrait_image_name(a_dto.getScreen_portrait_image_name());
		the_screen
				.setOrientation_locked(a_dto.isScreen_is_orientation_locked());
		the_screen.setOrientation(a_dto.getScreen_current_orientation());
		the_screen.setHuman_language_id(a_dto.getScreen_human_language_id());
		the_screen.setLabel(a_dto.getScreen_label());
		the_screen.setDescription(a_dto.getScreen_description());

		the_screen.setClient_device_type_label(a_dto
				.getScreen_client_device_type_label());
		the_screen.setClient_device_type_os_name(a_dto
				.getScreen_client_device_type_os_name());
		the_screen.setClient_device_type(a_dto.getScreen_client_device_type());
		the_screen.setClient_device_type_id(a_dto
				.getScreen_client_device_type_id());

		the_screen.setComponents(new ArrayList<GpUiWidgetX>());

		// this.handle_widgets(the_screen, dto_list, i);
		this.handle_screen_children(the_screen, dto_list);
		for (GpUiWidgetX a_widget : the_screen.getComponents()) {
			if (a_widget.getIs_container() != null) {
				this.handle_widget_children(the_screen, dto_list, a_widget);
			}
		}
		return the_screen;
	}

	// this adds all the widgets that are direct children of the screen
	private void handle_screen_children(GpScreenX the_screen,
			List<GpDto_screen_and_widgets> dto_list) {

		for (GpDto_screen_and_widgets the_dto : dto_list) {
			if (the_dto.getWidget_parent_id() == the_screen.getId()) {
				GpUiWidgetX the_widget = new GpUiWidgetX();
				this.handle_common_widget_properties(the_dto, the_widget);
				the_screen.getComponents().add(the_widget);
			}
		}
	}

	private void handle_widget_children(GpScreenX the_screen,
			List<GpDto_screen_and_widgets> dto_list, GpUiWidgetX the_container) {

		for (GpUiWidgetX the_widget : the_screen.getComponents()) {

			// Temporarily Hidden because of the GpScreenX don't have children

			// the_widget.setChildren(new ArrayList<GpUiWidget>());
			if (the_widget.getType()
					.equals(GpUiTypeConstants.GpTabBarContainer)
					|| the_widget.getType().equals(
							GpUiTypeConstants.GpAccordionContainer)) {

				this.handle_multi_section_container(the_widget, dto_list);

			} else if (the_widget.getType().equals(
					GpUiTypeConstants.GpBorderContainer)) {
				this.handle_single_section_container(the_widget, dto_list);

			} else if (the_widget.getType()
					.equals(GpUiTypeConstants.GpDataGrid)) {
				this.handle_data_grid(the_widget, dto_list);
			}
		}

	}

	/**
	 *
	 * @param the_screen
	 * @param the_container
	 * @param dto_list
	 *            </p> I realize that this can be combined with the
	 *            single_section_container method but</br> I want to be able to
	 *            have flexibility to add specific functionality by
	 *            container</p>
	 */

	private void handle_multi_section_container(GpUiWidgetX the_container,
			List<GpDto_screen_and_widgets> dto_list) {

		// first find sections to the main parent control
		for (GpDto_screen_and_widgets a_dto : dto_list) {

			if (a_dto.getWidget_parent_id() == the_container.getId()) {
				GpUiWidgetX a_section = new GpUiWidgetX();
				this.handle_common_widget_properties(a_dto, a_section);

				// Temporarily Hidden because of the GpScreenX don't have
				// children

				// a_section.setChildren(new ArrayList<GpUiWidgetX>());
				// the_container.getChildren().add(a_section);
			}

		}

		// second for each section find its children and add to section

		// Temporarily Hidden because of the GpScreenX don't have children

		/*
		 * for (GpUiWidgetX a_section : the_container.getChildren()) {
		 *
		 * for (GpDto_screen_and_widgets a_dto : dto_list) {
		 *
		 * if (a_dto.getWidget_parent_id() == a_section.getId()) {
		 * System.out.println("@@@@@@@  FOUND SECTION CHILD @@@@@@" +
		 * "\n section id is: " + a_section.getId() + "\n section name is: " +
		 * a_section.getName()); GpUiWidgetX a_widget = new GpUiWidgetX();
		 * this.handle_common_widget_properties(a_dto, a_widget); if
		 * (a_dto.getWidget_type().equals( GpUiTypeConstants.GpDataGrid)) { //
		 * a_widget.setChildren(new ArrayList<GpUiWidget>());
		 * this.handle_data_grid(a_widget, dto_list); }
		 * System.out.println("Adding found child to section " +
		 * "\n a_widget.getName() is: " + a_widget.getName() +
		 * "\n a_widget.getType()" + a_widget.getType());
		 * a_section.getChildren().add(a_widget); } } }
		 */

	}

	/**
	 *
	 * @param the_screen
	 * @param the_container
	 * @param dto_list
	 *            </p> I realize that this can be combined with the
	 *            handle_multi_section_container method but</br> I want to be
	 *            able to have flexibility to add specific functionality by
	 *            container</p>
	 */
	private void handle_single_section_container(GpUiWidgetX the_container,
			List<GpDto_screen_and_widgets> dto_list) {

		for (GpDto_screen_and_widgets a_dto : dto_list) {

			if (a_dto.getWidget_parent_id() == the_container.getId()) {

				GpUiWidgetX a_widget = new GpUiWidgetX();
				this.handle_common_widget_properties(a_dto, a_widget);

				if (a_dto.getWidget_type().equals(GpUiTypeConstants.GpDataGrid)) {

					// Temporarily Hidden because of the GpScreenX don't have
					// children

					// a_widget.setChildren(new ArrayList<GpUiWidget>());
					// this.handle_data_grid(a_widget, dto_list); // add all the
					// grids columns
				}

				// Temporarily Hidden because of the GpScreenX don't have
				// children

				// the_container.getChildren().add(a_widget);
			}

		}
	}

	/**
	 *
	 * @param the_screen
	 * @param the_container
	 * @param dto_list
	 *            </p> I realize that this can be combined with the
	 *            single_section_container method but</br> I think that Grids
	 *            will have more specific processing in the future
	 *            <p>
	 */
	private void handle_data_grid(GpUiWidgetX the_container,
			List<GpDto_screen_and_widgets> dto_list) {

		for (GpDto_screen_and_widgets a_dto : dto_list) {
			if (a_dto.getWidget_parent_id() == the_container.getId()) {
				GpUiWidgetX a_widget = new GpUiWidgetX();
				this.handle_common_widget_properties(a_dto, a_widget);

				// Temporarily Hidden because of the GpScreenX don't have
				// children

				// the_container.getChildren().add(a_widget);
			}

		}

	}

	private void handle_common_widget_properties(
			GpDto_screen_and_widgets dto_source, GpUiWidgetX widget_target) {

		widget_target.setId(dto_source.getWidget_id());
		widget_target.setDescription(dto_source.getWidget_description());
		widget_target.setLabel(dto_source.getWidget_label());
		widget_target.setName(dto_source.getWidget_name());
		widget_target.setNotes(dto_source.getWidget_notes());
		widget_target.setType(dto_source.getWidget_type());
		widget_target.setIs_container(dto_source.isWidget_is_container());

		widget_target.setData_binding_context(dto_source
				.getWidget_data_binding_context());
		widget_target.setExtended_attributes(dto_source
				.getWidget_extended_attributes());
		widget_target.setVerb_binding_context(dto_source
				.getWidget_verb_binding_context());
		widget_target.setEvent_verb_combo(dto_source
				.getWidget_event_verb_combo());
		widget_target.setVerb_target(dto_source.getWidget_verb_target());

		widget_target.setNoun_attribute_id(dto_source
				.getWidget_noun_attribute_id());
		widget_target.setNoun_id(dto_source.getWidget_noun_id());

		// widget_target.setNumber_of_children(0); //maybe we dont need this

		widget_target.setParentid(dto_source.getWidget_parent_id());
		widget_target.setParent_name(dto_source.getWidget_parent_name());
		widget_target.setScreen_id(dto_source.getScreen_id());
		widget_target.setSupports_label(dto_source.getWidget_supports_label());
		// widget_target.setUitechnology(dto_source);

		widget_target.setWidth(new Long(dto_source.getWidget_width())
				.longValue());
		widget_target.setHeight(new Long(dto_source.getWidget_height())
				.longValue());
		widget_target.setX(dto_source.getWidget_x());
		widget_target.setY(dto_source.getWidget_y());

		// Temporarily Hidden because of the GpScreenX don't have
		// Portrait_height and the Portrait_width

		// widget_target.setPortrait_height(new Long(dto_source
		// .getWidget_portrait_height()).longValue()); // do this until you
		// change the type
		// on the front end
		// widget_target.setPortrait_width(new Long(dto_source
		// .getWidget_portrait_width()).longValue());

		widget_target.setPortraitX(dto_source.getWidget_portrait_x());
		widget_target.setPortraitY(dto_source.getWidget_portrait_y());

		// Temporarily Hidden because of the GpScreenX don't have
		// Landscape_height and the Landscape_width

		// widget_target.setLandscape_height(new Long(dto_source
		// .getWidget_landscape_height()).longValue());
		// widget_target.setLandscape_width(new Long(dto_source
		// .getWidget_landscape_width()).longValue());

		widget_target.setLandscapeX(dto_source.getWidget_landscape_x());
		widget_target.setLandscapeY(dto_source.getWidget_landscape_y());

		widget_target.setCreatedate(dto_source.getWidget_createdate());
		widget_target.setCreatedby(dto_source.getWidget_createdby());
		widget_target.setLastmodifiedby(dto_source.getWidget_lastmodifiedby());
		widget_target.setLastmodifieddate(dto_source
				.getWidget_lastmodifieddate());

	}
}
