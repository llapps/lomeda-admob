(function() {
  // properties

  const path = require("path");
  const xmlHelper = require("../lib/xmlHelper.js");

  // entry
  module.exports = {
    read: read
  };

  // read CreativeAds config from config.xml
  function read(context) {
    const projectRoot = getProjectRoot(context);
    const configXml = getConfigXml(projectRoot);
    const creativeAdsXml = getCreativeAdsXml(configXml);
    const creativeAdsPreferences = getCreativeAdsPreferences(
      context,
      configXml,
      creativeAdsXml
    );

    validateCreativeAdsPreferences(creativeAdsPreferences);

    return creativeAdsPreferences;
  }

  // read config.xml
  function getConfigXml(projectRoot) {
    const pathToConfigXml = path.join(projectRoot, "config.xml");
    const configXml = xmlHelper.readXmlAsJson(pathToConfigXml);

    if (configXml == null) {
      throw new Error(
        "A config.xml is not found in project's root directory."
      );
    }

    return configXml;
  }

  // read <creativeads-config> within config.xml
  function getCreativeAdsXml(configXml) {
    const creativeAdsConfig = configXml.widget["creativeads-config"];

    if (creativeAdsConfig == null || creativeAdsConfig.length === 0) {
      throw new Error(
        "<creativeads-config> tag is not set in the config.xml."
      );
    }

    return creativeAdsConfig[0];
  }

  // read <creativeads-config> properties within config.xml
  function getCreativeAdsPreferences(context, configXml, creativeAdsXml) {
    return {
      projectRoot: getProjectRoot(context),
      projectName: getProjectName(configXml),
      appId: getConfigValue(creativeAdsXml, "app-id"),
      testDeviceId: getConfigValue(creativeAdsXml, "device-id"),
      isTest: getConfigValue(creativeAdsXml, "is-test"),
    };
  }

  // read project root from cordova context
  function getProjectRoot(context) {
    return context.opts.projectRoot || null;
  }

  // read project name from config.xml
  function getProjectName(configXml) {
    let output = null;
    if (configXml.widget.hasOwnProperty("name")) {
      const name = configXml.widget.name[0];
      if (typeof name === "string") {
        output = configXml.widget.name[0];
      } else {
        output = configXml.widget.name[0]._;
      }
    }

    return output;
  }

  // read CreativeAds value from <creativeads-config>
  function getConfigValue(creativeAdsXml, key) {
    return creativeAdsXml.hasOwnProperty(key) ? creativeAdsXml[key][0].$.value : null;
  }

  // validate <creativeads-config> properties within config.xml
  function validateCreativeAdsPreferences(preferences) {
    if (preferences.projectRoot === null) {
      throw new Error(
        'Invalid "root" in your config.xml.'
      );
    }
    if (preferences.projectName === null) {
      throw new Error(
        'Invalid "name" in your config.xml.'
      );
    }
    if (preferences.appId === null) {
      throw new Error(
        'Invalid "appId" in <creativeads-config> in your config.xml.'
      );
    }

    if (preferences.testDeviceId === null) {
      console.log('"testDeviceId" is not defined in <creativeads-config> in your config.xml.');
    }

    if (preferences.isTest === null) {
      console.log('"isTest" is not defined in <creativeads-config> in your config.xml. Assuming isTest = false');
    }
  }
})();

