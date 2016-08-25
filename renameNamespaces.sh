function move_folder() {
  oldFolder=$1;
  newFolder=$2;
  # Try SVN move
  if [ -d "$oldFolder" ]; then
    svn mv "$oldFolder" "$newFolder" > /dev/null;
  fi
  # If folder still exists, it is not versioned, so try a normal move
  if [ -d "$oldFolder" ]; then
    mv "$oldFolder" "$newFolder" > /dev/null;
  fi
}

function remove_folder() {
  folder=$1;
  # Try SVN remove
  if [ -d "$folder" ]; then
    svn rm "$folder" > /dev/null;
  fi
  # If folder still exists, it is not versioned, so try a normal remove
  if [ -d "$fodler" ]; then
    rm "$folder" > /dev/null;
  fi
}

function migrate_project() { 
  project=$1
  # Move edu folders
  EDUFOLDERS=$(find $project -type d -name "edu" -not -path "*bin*");
  for folder in $EDUFOLDERS; do
    echo "Move folder $folder/kit/ipd/sdq/vitruvius";
    svn mkdir --parents "$folder/../tools/vitruvius" > /dev/null;
    move_folder "$folder/kit/ipd/sdq/vitruvius/*" "$folder/../tools/vitruvius";
    #  Remove the old folders. Some may not be versioned (e.g. xtend-gen), so they have to be removed without svn
    remove_folder "$folder/kit/ipd/sdq/vitruvius";
    remove_folder "$folder/kit/ipd/sdq/";
    remove_folder "$folder/kit/ipd/";
    remove_folder "$folder/kit/";
    remove_folder "$folder/";
  done

  # Replace references in files
  FILES=$(find $project -type f -not -name "*.class" -not -name "*._trace" -not -name "*.xtendbin");
  for file in $FILES; do
    echo "Update file $file";
    sed -i "s/edu.kit.ipd.sdq.vitruvius/tools.vitruvius/g" $file;
  done
  
  NAMEFILES=$(find $project -name "*edu.kit.ipd.sdq.vitruvius*");
  for file in $NAMEFILES; do
    echo "Rename file $file";
    newFileName=$(echo "$file" | sed 's/edu.kit.ipd.sdq.vitruvius/tools.vitruvius/g');
    svn mv "$file" "$newFileName" > /dev/null;
  done

  # Rename project
  newProjectName=$(echo "$project" | sed 's/edu.kit.ipd.sdq.vitruvius/tools.vitruvius/g');
  move_folder "$project" "$newProjectName";
}

# Migrate the namespaces of each project in the current folder
for project in $(ls .); do   
  echo "------------------------";   
  echo "Migrate project $project";   
  migrate_project $project; 
done
