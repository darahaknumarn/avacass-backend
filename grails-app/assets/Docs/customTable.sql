-- done
INSERT INTO `requestmap` (`version`, `config_attribute`, `url`) VALUES ('0', 'isAuthenticated()', '/api/deliveryAddress');


-- done
INSERT INTO `enumerations` (`e_type`, `is_active`, `is_deleted`, `code`, `name`, `is_default`, `description`,version) VALUES
('PROVINCE', b'1', b'0', 'pp', 'Phnom Penh', b'1', 'រាជធានីភ្នំពេញ' , 0);


INSERT INTO `requestmap` (`version`, `config_attribute`, `url`) VALUES ('0', 'isAuthenticated()', '/api/enumeration/**');
INSERT INTO `dev_avacass`.`requestmap` (`version`, `config_attribute`, `url`) VALUES ('0', 'isAuthenticated()', '/api/enumerationDefault/**');

-- done
INSERT INTO `next_code` (`version`, `latest_code`, `digit`, `nv_type`, `format_code`) VALUES ('0', '1', '4', 'Order', '${code}');
INSERT INTO `next_code` (`version`, `latest_code`, `digit`, `nv_type`, `format_code`) VALUES ('0', '1', '6', 'Customer', '${code}');
INSERT INTO `enumerations` (`version`, `e_type`, `is_active`, `is_deleted`, `name`, `is_default`) VALUES ('0', 'PAYMENTSTATUS', b'1', b'0', 'Unpaid', b'1');
INSERT INTO `enumerations` (`version`, `e_type`, `is_active`, `is_deleted`, `name`, `is_default`) VALUES (b'0', 'PAYMENTSTATUS', b'1', b'0', 'Paid', b'0');

-- done
INSERT INTO `requestmap` (`version`, `config_attribute`, `url`) VALUES ('0', 'isAuthenticated()', '/api/customer/**');
INSERT INTO `requestmap` (`version`, `config_attribute`, `url`) VALUES ('0', 'isAuthenticated()', '/api/orders/**');
INSERT INTO `requestmap` (`version`, `config_attribute`, `url`) VALUES ('0', 'isAuthenticated()', '/api/category/**');
INSERT INTO `requestmap` (`version`, `config_attribute`, `url`) VALUES ('0', 'isAuthenticated()', '/api/product/**');
INSERT INTO `requestmap` (`version`, `config_attribute`, `url`) VALUES ('0', 'isAuthenticated()', '/api/vendor/**');
