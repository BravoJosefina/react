/**
 * Optional custom JavaScript bundle(s)
 * The following is the definition of your "app" js bundle. It will pass your code through UIToolKit's Frontend Builder.
 * Paths below are relative to "assets/js/" and a bundle can be defined by a single path or array of paths.
 */
module.exports = {
    // Default base path: sourceBaseDirectory || './src/main/resources/assets/'
    reactEntryPoints: {
        // Create react module using webpack
        // Default reactEntryPoints: `${sourceBaseDirectory}react/`
        // Generate a bundle, {value: key} = {bundle name : path to module file.}
        'input-example': `./src/main/resources/assets/js/react/components/input-example.jsx`,
    },
    //// Create css using sass [optional if using default base path]
    //// Default sassEntryPoints: `${sourceBaseDirectory}sass/`
    // sassEntryPoints: ['./src/main/resource/assets/sass/*-core.scss'],
    bundles: {
        app: {
            sources: [
                'vendors/**/*.js',  /* you can put 3rd party vendor code here */
                'models/**/*.js',
                'presenters/**/*.js',
            ],
            includeTemplates: 'end'
        }
    },
};