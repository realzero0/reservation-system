var gulp = require('gulp');
var concat = require('gulp-concat');
var stripDebug = require('gulp-strip-debug');
var uglify = require('gulp-uglify');
//...

gulp.task('bicycle-combine-js', [], function () {
    return gulp.src('src/*.js')
        .pipe(stripDebug())
        .pipe(uglify())
        .pipe(concat('app.js'))
        .pipe(gulp.dest('dist'));
});

gulp.task('bicycle-myreservation-combine-js', [], function(){
  return gulp.src(['myreservationApp.js'])
  .pipe(stripDebug())
  .pipe(uglify())
  .pipe(concat('myreservation.js'))
  .pipe(gulp.dest('dist'));
})
