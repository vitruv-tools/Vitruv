# Migrates the namespaces in project given as $1
function migrate_project() { 
  project=$1
  # Move edu folders
  EDUFOLDERS=$(find $project -type d -name "edu" -not -path "bin");
  for folder in $EDUFOLDERS; do
    echo "Move folder $folder/kit.ipd.sdq.vitruvius";
    svn mkdir --parents "$folder/../tools/vitruvius" > /dev/null;
    svn mv "$folder/kit/ipd/sdq/vitruvius/*" "$folder/../tools/vitruvius" > /dev/null;
    svn rm "$folder/kit/ipd/sdq/vitruvius" > /dev/null;
    if [ "$(ls -A $folder/kit/ipd/sdq/)" ]; then
      svn rm "$folder/kit/ipd/sdq" > /dev/null;
    fi
    if [ "$(ls -A $folder/kit/ipd/)" ]; then
      svn rm "$folder/kit/ipd/" > /dev/null;   
    fi
    if [ "$(ls -A $folder/kit/)" ]; then
      svn rm "$folder/kit/" > /dev/null; 
    fi
    if [ "$(ls -A $folder/)" ]; then
      svn rm "$folder" > /dev/null;
    fi
  done

  # Replace references in files
  FILES=$(find $project -type f -not -name "*.class" -not -name "*._trace" -not -name "*.xtendbin");
  for file in $FILES; do
    echo "Update file $file";
    sed -i "s/edu.kit.ipd.sdq.vitruvius/tools.vitruvius/g" $file;
  done
  
  # Rename project
  newProjectName=$(echo "$project" | sed 's/edu.kit.ipd.sdq.vitruvius/tools.vitruvius/g');
  svn mv "$project" "$newProjectName";
}

# Migrate the namespaces of each project in the current folder
for project in $(ls .); do   
  echo "------------------------";   
  echo "Migrate project $project";   
  migrate_project $project; 
done
