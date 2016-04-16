var gulp = require('gulp');
var url = require('url');
var proxy = require('proxy-middleware');
var templateCache = require('gulp-angular-templatecache');
var browserSync = require("browser-sync").create();

$ = require('gulp-load-plugins')();

gulp.task('clean', function () {
    return gulp.src(['dist/', '.tmp/'])
            .pipe($.clean());
});

gulp.task('templates', function () {
    return gulp.src('app/views/**/*.html')
            .pipe(templateCache({module: 'exampleApp.templates', standalone: true}))
            .pipe($.wrap('(function () {<%=contents%>}());'))
            .pipe(gulp.dest('.tmp/scripts/'));
});

gulp.task('serve', ['templates'], function () {
    var proxyOptions = url.parse('http://localhost:9000/api');
    proxyOptions.route = '/api';
    browserSync.init({
        notify: false,
        // Customize the Browsersync console logging prefix
        logPrefix: 'WSK',
        // Allow scroll syncing across breakpoints
        //scrollElementMapping: ['main', '.mdl-layout'],
        // Run as an https by uncommenting 'https: true'
        // Note: this uses an unsigned certificate which on first access
        //       will present a certificate warning in the browser.
        // https: true,
        server: {
            baseDir: ['.tmp', 'app'],
            middleware: [proxy(proxyOptions)]
        },
        port: 3000
    });

    gulp.watch(['app/**/*.html', 'app/styles/**/*.{scss,css}', 'app/scripts/**/*.js', 'app/images/**/*'], ['templates', browserSync.reload]);
//  gulp.watch(['app/styles/**/*.{scss,css}'], ['styles', browserSync.reload]);
//  gulp.watch(['app/scripts/**/*.js'], ['lint', 'scripts']);
//  gulp.watch(['app/images/**/*'], browserSync.reload);
});

gulp.task('copy-fonts', function () {
    gulp.src(
            [
                'bower_components/font-awesome/fonts/*.*',
                'bower_components/bootstrap-material-design/fonts/*.*',
                'bower_components/bootstrap/fonts/*.*'
            ],
            {cwd: 'app/'})
            .pipe(gulp.dest('dist/fonts/'));
});

gulp.task('copy-images', function () {
    gulp.src(['images/**'],
            {cwd: 'app/'})
            .pipe($.cache($.imagemin({optimizationLevel: 5, progressive: true, interlaced: true})))
            .pipe(gulp.dest('dist/images/'));
});

gulp.task('copy-i18n', function () {
    gulp.src(['i18n/**'],{cwd: 'app/'})
            .pipe(gulp.dest('dist/i18n/'));
});

gulp.task('copy', ['copy-fonts', 'copy-images', 'copy-i18n'], function () {});

//gulp.task('less', function () {
//  return gulp.src(['bower_components/bootstrap/less/bootstrap.less', 'bower_components/font-awesome/less/font-awesome.less'])
//    .pipe($.less())
//    .pipe($.concat())
//    .pipe(gulp.dest('./tmp/styles'));
//});
//
//var sass = require('gulp-ruby-sass');
//gulp.task('sass', function() {
//    return sass('src/scss/style.scss', {style: 'compressed'})
//        .pipe(rename({suffix: '.min'}))
//        .pipe(gulp.dest('build/css'));
//});

gulp.task('usemin', ['copy'], function () {
    return gulp.src('app/index.html')
            .pipe($.usemin({
                css: [
                    $.minifyCss(),
                    'concat',
                    $.rev()
                ],
                appcss: [
                    $.minifyCss(),
                    $.rev()
                ],
                js: [
                    $.uglify(),
                    $.rev()
                ],
                ngjs: [
                    $.stripDebug(),
                    $.ngAnnotate(),
                    //'concat'
                    $.uglify(),
                    $.rev()
                ],
                tpljs: [
                    $.ngAnnotate(),
                    $.uglify(),
                    $.rev()
                ]
            }))
            .pipe(gulp.dest('dist/'));
});
// Tell Gulp what to do when we type "gulp" into the terminal
gulp.task('default', function (cb) {
    $.sequence('clean', 'templates', 'usemin')(cb);
});