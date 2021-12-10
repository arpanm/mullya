import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './catalogue.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CatalogueDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const catalogueEntity = useAppSelector(state => state.catalogue.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="catalogueDetailsHeading">
          <Translate contentKey="mulyaaApp.catalogue.detail.title">Catalogue</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{catalogueEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="mulyaaApp.catalogue.name">Name</Translate>
            </span>
          </dt>
          <dd>{catalogueEntity.name}</dd>
          <dt>
            <span id="stockImageUrl">
              <Translate contentKey="mulyaaApp.catalogue.stockImageUrl">Stock Image Url</Translate>
            </span>
          </dt>
          <dd>{catalogueEntity.stockImageUrl}</dd>
          <dt>
            <span id="landingUrl">
              <Translate contentKey="mulyaaApp.catalogue.landingUrl">Landing Url</Translate>
            </span>
          </dt>
          <dd>{catalogueEntity.landingUrl}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="mulyaaApp.catalogue.description">Description</Translate>
            </span>
          </dt>
          <dd>{catalogueEntity.description}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="mulyaaApp.catalogue.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{catalogueEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdOn">
              <Translate contentKey="mulyaaApp.catalogue.createdOn">Created On</Translate>
            </span>
          </dt>
          <dd>
            {catalogueEntity.createdOn ? <TextFormat value={catalogueEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="mulyaaApp.catalogue.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{catalogueEntity.createdBy}</dd>
          <dt>
            <span id="updatedOn">
              <Translate contentKey="mulyaaApp.catalogue.updatedOn">Updated On</Translate>
            </span>
          </dt>
          <dd>
            {catalogueEntity.updatedOn ? <TextFormat value={catalogueEntity.updatedOn} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="mulyaaApp.catalogue.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{catalogueEntity.updatedBy}</dd>
          <dt>
            <Translate contentKey="mulyaaApp.catalogue.parent">Parent</Translate>
          </dt>
          <dd>{catalogueEntity.parent ? catalogueEntity.parent.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/catalogue" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/catalogue/${catalogueEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CatalogueDetail;
