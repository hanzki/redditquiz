module.exports = function(grunt) {
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    concat: {
      options: {
        separator: ';',
      },
      dist: {
        src: ['js-src/**/*.js'],
        dest: 'public/js/app.js',
      },
    },
    watch: {
      css: {
        files: ['**/*.scss', 'js-src/**/*.js'],
        tasks: ['concat']
      }
    },
    bower_concat: {
      all: {
        dest: 'public/build/_bower.js',
        cssDest: 'public/build/_bower.css',
        bowerOptions: {
          relative: false
        }
      }
    },
    build: {
      all: {
        tasks: ['concat', 'bower_concat']
      }
    }

  });
  grunt.loadNpmTasks('grunt-contrib-sass');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-bower-concat');
  grunt.registerTask('default',['watch']);
  grunt.registerTask('build',['concat', 'bower_concat']);
}
