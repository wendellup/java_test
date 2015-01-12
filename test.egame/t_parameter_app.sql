use egame_game;
INSERT INTO `t_parameter_app` VALUES (915, 910, 7, '搜索权重月排行', 'is_rank_month_weight=1', 9999, 1, 1, '', NULL, '1970-1-1 08:00:00', '4000-1-1 08:00:00', '1970-1-1 08:00:00', 0);
insert into `t_game_tag` (tag_name, tag_type, enable)
values (915, 2, 1);
INSERT INTO `t_parameter_sys` VALUES ('is_show_recommend_games', '1');
INSERT INTO `t_parameter_app` VALUES (727, 0, 0, '个性化推荐列表', 'channel_page_type=22,channel_type=2,ref_sort_type=1105', 9999, 1, 1, '11', NULL, '1970-1-1 08:00:00', '4000-1-1 08:00:00', '1970-1-1 08:00:00', 0);

