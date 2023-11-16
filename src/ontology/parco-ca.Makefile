## Customize Makefile settings for parco-ca
## 
## If you need to customize your Makefile, make
## changes here rather than in the main Makefile

.PHONY: all
all: templates ../templates/classes.tsv components/vocabulary.owl all_odk 

TEMPLATESDIR=../templates

$(TEMPLATESDIR)/classes.tsv: ../scripts/convert.groovy
	groovy ../scripts/convert.groovy $(TEMPLATESDIR)/vocabulary.csv

$(TEMPLATESDIR)/classes.owl: $(TEMPLATESDIR)/classes.tsv $(SRC)
	$(ROBOT) merge -i $(SRC) template --prefix "vocab: http://si.eu-parc.eu/PARCO-" --prefix "dct: http://purl.org/dc/terms/" --template $< --output $@ && \
			  $(ROBOT) annotate --input $@ --ontology-iri $(ONTBASE)/components/$*.owl -o $@
		  
components/classes.owl: $(TEMPLATES)
	  $(ROBOT) merge $(patsubst %, -i %, $^) \
			    annotate --ontology-iri $(ONTBASE)/$@ --version-iri $(ONTBASE)/releases/$(TODAY)/$@ \
					    --output $@.tmp.owl && mv $@.tmp.owl $@
		    
templates: $(TEMPLATES)
	  echo $(TEMPLATES)
