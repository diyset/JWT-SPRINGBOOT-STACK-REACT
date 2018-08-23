const {injectBabelPlugin} = require('react-app-rewired');
const rewireLess = require('react-app-rewire-less');

module.exports = function override(config, env) {
    config = injectBabelPlugin(['import',{libraryName:'ant',style:true}],config);
    config = rewireLess.withLoaderOptions({
        modifyVars:{
            "@layout-body-background": "#FFFFFF",
            "@layout-header-background": "#FFFFFF",
            "@layout-footer-background": "#FFFFFF"
        },
        javascriptEnabled:true
    })(config, env);
    return config;
    
}